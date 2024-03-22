package org.example.model;

import lombok.Data;

import java.util.List;

@Data
public class MimeType {
    String name;
    String description;
    Relationship links;
    List<String> fileTypes;
    List<Link> furtherReading;
    Notice notices;
    boolean deprecated;
    String useInstead;
}
