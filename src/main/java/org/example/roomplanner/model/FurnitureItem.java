package org.example.roomplanner.model;

public class FurnitureItem {
    public final String name;
    public final String thumbPath;  // путь к PNG-превью
    public final String modelPath;  // путь к OBJ

    public FurnitureItem(String name, String thumbPath, String modelPath) {
        this.name = name;
        this.thumbPath = thumbPath;
        this.modelPath = modelPath;
    }
}
