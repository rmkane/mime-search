package org.example.model;

import lombok.Data;

import java.util.List;

@Data
public class Relationship {
    List<String> deprecates;
    List<String> relatedTo;
    List<String> parentOf;
    List<String> alternativeTo;
}
