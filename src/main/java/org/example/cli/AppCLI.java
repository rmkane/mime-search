package org.example.cli;

import lombok.AllArgsConstructor;
import org.example.service.MimeTypeService;

import java.util.Collection;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.stream.Collectors;

public class AppCLI implements Runnable {
    private static final String BANNER = """
           ██╗    ██╗███████╗██╗      ██████╗ ██████╗ ███╗   ███╗███████╗
           ██║    ██║██╔════╝██║     ██╔════╝██╔═══██╗████╗ ████║██╔════╝
           ██║ █╗ ██║█████╗  ██║     ██║     ██║   ██║██╔████╔██║█████╗
           ██║███╗██║██╔══╝  ██║     ██║     ██║   ██║██║╚██╔╝██║██╔══╝
           ╚███╔███╔╝███████╗███████╗╚██████╗╚██████╔╝██║ ╚═╝ ██║███████╗
            ╚══╝╚══╝ ╚══════╝╚══════╝ ╚═════╝ ╚═════╝ ╚═╝     ╚═╝╚══════╝
            """;

    private final MimeTypeService mimeTypeService;

    private Scanner scanner;
    private boolean running;

    public AppCLI(MimeTypeService mimeTypeService) {
        this.mimeTypeService = mimeTypeService;
    }

    @Override
    public void run() {
        scanner = new Scanner(System.in);
        running = true;

        displayBanner();

        while (running) {
            displayMenu();
        }

        scanner.close();
    }

    private void displayBanner() {
        System.out.printf("%s%n", BANNER);
    }

    private void displayMenu() {
        System.out.println("""
                Choose and option:
                1. Find file extensions by MIME type
                2. Find MIME types by file extension
                3. Quit
                """);
        System.out.print(">>> ");
        int choice;

        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            choice = -1;
        } finally {
            scanner.nextLine();
        }

        System.out.println(); // Spacer

        switch (choice) {
            case 1 -> {
                System.out.print("What is the MIME type?: ");
                String mimeType = scanner.nextLine();
                System.out.printf("File extensions for type '%s':%n%n", mimeType);
                displayList(mimeTypeService.findFileTypesByName(mimeType));
            }
            case 2 -> {
                System.out.print("What is the file extension?: ");
                String fileType = scanner.nextLine();
                System.out.printf("MIME types for file extension: '%s':%n%n", fileType);
                displayList(mimeTypeService.findNamesByFileType(fileType));
            }
            case 3 -> {
                running = false;
                System.out.println("Goodbye");
            }
            default -> {
                System.out.println("Invalid option");
            }
        }
    }

    private <E> String formatList(Collection<E> collection, String bulletType) {
        return collection.stream()
                .map(item -> String.format("%s %s", bulletType, item))
                .collect(Collectors.joining(System.lineSeparator()));
    }

    private <E> void displayList(Collection<E> collection) {
        System.out.printf("%s%n%n", formatList(collection, "-"));
    }

}
