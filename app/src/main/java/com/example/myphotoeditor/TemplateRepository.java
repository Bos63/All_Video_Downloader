package com.example.myphotoeditor;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class TemplateRepository {

    public static List<TemplateModel> getTemplates() {
        List<TemplateModel> list = new ArrayList<>();

        list.add(new TemplateModel("p1", "Polaroid Soft", "Polaroid", Color.WHITE, Color.LTGRAY, 24, 18, 0));
        list.add(new TemplateModel("p2", "Polaroid Thick", "Polaroid", Color.WHITE, Color.GRAY, 30, 8, 0));
        list.add(new TemplateModel("p3", "Polaroid Retro", "Polaroid", Color.parseColor("#FDF7E3"), Color.parseColor("#CCBFA3"), 26, 10, 0));
        list.add(new TemplateModel("p4", "Polaroid Night", "Polaroid", Color.parseColor("#FAFAFA"), Color.parseColor("#111111"), 22, 16, 0));
        list.add(new TemplateModel("p5", "Polaroid Rounded", "Polaroid", Color.WHITE, Color.parseColor("#DDDDDD"), 20, 24, 0));
        list.add(new TemplateModel("p6", "Polaroid Classic", "Polaroid", Color.parseColor("#FFFDF8"), Color.parseColor("#B0B0B0"), 28, 6, 0));

        list.add(new TemplateModel("s1", "Story Sunset", "Story", Color.parseColor("#55FFFFFF"), Color.parseColor("#FF6F00"), 8, 12, 2));
        list.add(new TemplateModel("s2", "Story Purple", "Story", Color.parseColor("#66FFFFFF"), Color.parseColor("#7C4DFF"), 8, 12, 2));
        list.add(new TemplateModel("s3", "Story Aqua", "Story", Color.parseColor("#66FFFFFF"), Color.parseColor("#00BCD4"), 8, 12, 2));
        list.add(new TemplateModel("s4", "Story Dark", "Story", Color.parseColor("#66FFFFFF"), Color.parseColor("#263238"), 8, 12, 2));
        list.add(new TemplateModel("s5", "Story Warm", "Story", Color.parseColor("#66FFFFFF"), Color.parseColor("#FF7043"), 8, 12, 2));
        list.add(new TemplateModel("s6", "Story Mint", "Story", Color.parseColor("#66FFFFFF"), Color.parseColor("#26A69A"), 8, 12, 2));

        list.add(new TemplateModel("m1", "Minimal Thin", "Minimal", Color.WHITE, Color.TRANSPARENT, 4, 8, 0));
        list.add(new TemplateModel("m2", "Minimal Round", "Minimal", Color.parseColor("#EEEEEE"), Color.TRANSPARENT, 6, 28, 0));
        list.add(new TemplateModel("m3", "Minimal Gray", "Minimal", Color.parseColor("#BDBDBD"), Color.TRANSPARENT, 5, 14, 0));
        list.add(new TemplateModel("m4", "Minimal Soft", "Minimal", Color.parseColor("#FAFAFA"), Color.TRANSPARENT, 3, 18, 0));

        list.add(new TemplateModel("n1", "Neon Pink", "Neon", Color.parseColor("#FF4DFF"), Color.parseColor("#FF4DFF"), 12, 14, 0));
        list.add(new TemplateModel("n2", "Neon Blue", "Neon", Color.parseColor("#40C4FF"), Color.parseColor("#40C4FF"), 12, 14, 0));
        list.add(new TemplateModel("n3", "Neon Lime", "Neon", Color.parseColor("#69F0AE"), Color.parseColor("#69F0AE"), 12, 14, 0));
        list.add(new TemplateModel("n4", "Neon Gold", "Neon", Color.parseColor("#FFD740"), Color.parseColor("#FFD740"), 12, 14, 0));

        list.add(new TemplateModel("h1", "Holiday Star", "Holiday", Color.WHITE, Color.parseColor("#FFC107"), 8, 12, 12));
        list.add(new TemplateModel("h2", "Party Confetti", "Holiday", Color.WHITE, Color.parseColor("#FF4081"), 8, 12, 16));
        list.add(new TemplateModel("h3", "Balloon Pop", "Holiday", Color.WHITE, Color.parseColor("#7E57C2"), 8, 12, 8));
        list.add(new TemplateModel("h4", "Sparkle", "Holiday", Color.WHITE, Color.parseColor("#26C6DA"), 8, 12, 10));

        list.add(new TemplateModel("b1", "Badge Red", "Badge", Color.WHITE, Color.parseColor("#E53935"), 7, 12, 1));
        list.add(new TemplateModel("b2", "Badge Blue", "Badge", Color.WHITE, Color.parseColor("#1E88E5"), 7, 12, 1));

        list.add(new TemplateModel("f1", "Film Black", "Film", Color.parseColor("#111111"), Color.WHITE, 20, 0, 14));
        list.add(new TemplateModel("f2", "Film Gray", "Film", Color.parseColor("#424242"), Color.WHITE, 20, 0, 12));

        return list;
    }
}
