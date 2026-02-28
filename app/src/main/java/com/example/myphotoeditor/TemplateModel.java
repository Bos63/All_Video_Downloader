package com.example.myphotoeditor;

public class TemplateModel {
    public final String id;
    public final String name;
    public final String category;
    public final int borderColor;
    public final int accentColor;
    public final float borderWidth;
    public final float cornerRadius;
    public final int decoCount;

    public TemplateModel(String id, String name, String category, int borderColor, int accentColor,
                         float borderWidth, float cornerRadius, int decoCount) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.borderColor = borderColor;
        this.accentColor = accentColor;
        this.borderWidth = borderWidth;
        this.cornerRadius = cornerRadius;
        this.decoCount = decoCount;
    }
}
