package org.example.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.example.App;
import org.example.model.MimeType;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class MimeTypeService {
    private static final String DATA_LOCATION = "mimeData.json";

    private static final List<MimeType> mimeTypeRepo = loadTypes();

    public List<String> findFileTypesByName(String name) {
        String lowerName = name.toLowerCase();
        return mimeTypeRepo.stream()
                .filter(type -> type.getName().toLowerCase().contains(lowerName))
                .flatMap(type -> type.getFileTypes().stream())
                .toList();
    }

    public List<String> findNamesByFileType(String fileType) {
        String lowerFileType = fileType.toLowerCase();
        return mimeTypeRepo.stream()
                .filter(type -> type.getFileTypes().stream().anyMatch(ext -> ext.contains(lowerFileType)))
                .map(MimeType::getName)
                .toList();
    }

    private static List<MimeType> loadTypes() {
        ClassLoader classLoader = App.class.getClassLoader();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new ParameterNamesModule());

        try (InputStream is = classLoader.getResourceAsStream(DATA_LOCATION)) {
            return objectMapper.readValue(is, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
