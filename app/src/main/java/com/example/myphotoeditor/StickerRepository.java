package com.example.myphotoeditor;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class StickerRepository {

    public static List<StickerModel> getAll() {
        List<StickerModel> list = new ArrayList<>();

        list.add(new StickerModel("s1", "Heart", "Sosyal", "HEART", Color.RED));
        list.add(new StickerModel("s2", "Like", "Sosyal", "LIKE", Color.parseColor("#1976D2")));
        list.add(new StickerModel("s3", "Star", "Sosyal", "STAR", Color.parseColor("#FFC107")));
        list.add(new StickerModel("s4", "Smile", "Sosyal", "SMILE", Color.parseColor("#FFEB3B")));
        list.add(new StickerModel("s5", "Chat", "Sosyal", "CHAT", Color.WHITE));
        list.add(new StickerModel("s6", "Share", "Sosyal", "SHARE", Color.parseColor("#00BCD4")));
        list.add(new StickerModel("s7", "Fire", "Sosyal", "FIRE", Color.parseColor("#FF5722")));
        list.add(new StickerModel("s8", "Gift", "Sosyal", "GIFT", Color.parseColor("#EC407A")));
        list.add(new StickerModel("s9", "Clap", "Sosyal", "CLAP", Color.parseColor("#FFCA28")));
        list.add(new StickerModel("s10", "Bubble", "Sosyal", "BUBBLE", Color.parseColor("#90CAF9")));

        list.add(new StickerModel("g1", "Arrow", "Geometrik", "ARROW", Color.WHITE));
        list.add(new StickerModel("g2", "Circle", "Geometrik", "CIRCLE", Color.parseColor("#69F0AE")));
        list.add(new StickerModel("g3", "Triangle", "Geometrik", "TRIANGLE", Color.parseColor("#FF8A65")));
        list.add(new StickerModel("g4", "Wave", "Geometrik", "WAVE", Color.parseColor("#80DEEA")));
        list.add(new StickerModel("g5", "Hex", "Geometrik", "HEX", Color.parseColor("#B39DDB")));
        list.add(new StickerModel("g6", "Diamond", "Geometrik", "DIAMOND", Color.parseColor("#FFCC80")));

        list.add(new StickerModel("r1", "SALE", "Rozet", "BADGE_SALE", Color.parseColor("#E53935")));
        list.add(new StickerModel("r2", "NEW", "Rozet", "BADGE_NEW", Color.parseColor("#1E88E5")));
        list.add(new StickerModel("r3", "BEST", "Rozet", "BADGE_BEST", Color.parseColor("#43A047")));
        list.add(new StickerModel("r4", "WOW", "Rozet", "BADGE_WOW", Color.parseColor("#8E24AA")));
        list.add(new StickerModel("r5", "TOP", "Rozet", "BADGE_TOP", Color.parseColor("#FB8C00")));
        list.add(new StickerModel("r6", "PRO", "Rozet", "BADGE_PRO", Color.parseColor("#3949AB")));

        list.add(new StickerModel("m1", "Camera", "Sembol", "CAMERA", Color.WHITE));
        list.add(new StickerModel("m2", "Music", "Sembol", "MUSIC", Color.parseColor("#CE93D8")));
        list.add(new StickerModel("m3", "Pin", "Sembol", "PIN", Color.parseColor("#EF9A9A")));
        list.add(new StickerModel("m4", "Crown", "Sembol", "CROWN", Color.parseColor("#FFD54F")));
        list.add(new StickerModel("m5", "Bolt", "Sembol", "BOLT", Color.parseColor("#FFF176")));
        list.add(new StickerModel("m6", "Moon", "Sembol", "MOON", Color.parseColor("#B3E5FC")));
        list.add(new StickerModel("m7", "Sun", "Sembol", "SUN", Color.parseColor("#FFCC80")));
        list.add(new StickerModel("m8", "Leaf", "Sembol", "LEAF", Color.parseColor("#A5D6A7")));

        return list;
    }
}
