package org.example;

import org.example.cli.AppCLI;
import org.example.service.MimeTypeService;

public class App {
    private static final MimeTypeService mimeTypeService = new MimeTypeService();

    public static void main(String[] args) {
        new AppCLI(mimeTypeService).run();
    }
}
