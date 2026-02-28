package com.example.myphotoeditor;

public class StickerModel {
    public final String id;
    public final String name;
    public final String category;
    public final String type;
    public final int defaultColor;

    public StickerModel(String id, String name, String category, String type, int defaultColor) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.type = type;
        this.defaultColor = defaultColor;
    }
}
