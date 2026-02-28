package com.example.myphotoeditor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;

public class TemplateRenderer {

    public static Bitmap createThumbnail(TemplateModel model, int w, int h) {
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint bg = new Paint(Paint.ANTI_ALIAS_FLAG);
        bg.setColor(0xFF444444);
        canvas.drawRect(0, 0, w, h, bg);
        drawTemplate(canvas, model, w, h);
        return bmp;
    }

    public static void drawTemplate(Canvas canvas, TemplateModel model, int w, int h) {
        if (model == null) return;

        Paint border = new Paint(Paint.ANTI_ALIAS_FLAG);
        border.setStyle(Paint.Style.STROKE);
        border.setStrokeWidth(model.borderWidth);
        border.setColor(model.borderColor);

        RectF rect = new RectF(model.borderWidth / 2f, model.borderWidth / 2f, w - model.borderWidth / 2f, h - model.borderWidth / 2f);
        canvas.drawRoundRect(rect, model.cornerRadius, model.cornerRadius, border);

        switch (model.category) {
            case "Polaroid":
                drawPolaroidFooter(canvas, w, h, model);
                break;
            case "Story":
                drawStoryBands(canvas, w, h, model);
                break;
            case "Neon":
                drawNeonGlow(canvas, w, h, model);
                break;
            case "Holiday":
                drawConfetti(canvas, w, h, model.decoCount, model.accentColor);
                break;
            case "Badge":
                drawBadgeRibbon(canvas, w, model.accentColor);
                break;
            case "Film":
                drawFilmHoles(canvas, w, h, model);
                break;
        }
    }

    private static void drawPolaroidFooter(Canvas canvas, int w, int h, TemplateModel model) {
        Paint footer = new Paint(Paint.ANTI_ALIAS_FLAG);
        footer.setColor(model.borderColor);
        canvas.drawRect(0, h - (model.borderWidth * 2.8f), w, h, footer);
    }

    private static void drawStoryBands(Canvas canvas, int w, int h, TemplateModel model) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        Shader top = new LinearGradient(0, 0, 0, h * 0.2f, model.accentColor, 0x00000000, Shader.TileMode.CLAMP);
        p.setShader(top);
        canvas.drawRect(0, 0, w, h * 0.2f, p);
        Shader bottom = new LinearGradient(0, h, 0, h * 0.8f, model.accentColor, 0x00000000, Shader.TileMode.CLAMP);
        p.setShader(bottom);
        canvas.drawRect(0, h * 0.8f, w, h, p);
    }

    private static void drawNeonGlow(Canvas canvas, int w, int h, TemplateModel model) {
        Paint glow = new Paint(Paint.ANTI_ALIAS_FLAG);
        glow.setStyle(Paint.Style.STROKE);
        glow.setStrokeWidth(model.borderWidth + 10);
        glow.setColor(model.accentColor);
        glow.setAlpha(90);
        RectF r = new RectF(12, 12, w - 12, h - 12);
        canvas.drawRoundRect(r, model.cornerRadius + 10, model.cornerRadius + 10, glow);
    }

    private static void drawConfetti(Canvas canvas, int w, int h, int count, int color) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(color);
        for (int i = 0; i < count; i++) {
            float x = (i * 37) % w;
            float y = (i * 53) % h;
            canvas.drawCircle(x, y, 4 + (i % 4), p);
        }
    }

    private static void drawBadgeRibbon(Canvas canvas, int w, int color) {
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(color);
        Path path = new Path();
        path.moveTo(w * 0.68f, 0);
        path.lineTo(w, 0);
        path.lineTo(w, w * 0.32f);
        path.close();
        canvas.drawPath(path, p);
    }

    private static void drawFilmHoles(Canvas canvas, int w, int h, TemplateModel model) {
        Paint bg = new Paint(Paint.ANTI_ALIAS_FLAG);
        bg.setColor(model.borderColor);
        canvas.drawRect(0, 0, w, model.borderWidth * 1.5f, bg);
        canvas.drawRect(0, h - model.borderWidth * 1.5f, w, h, bg);

        Paint holes = new Paint(Paint.ANTI_ALIAS_FLAG);
        holes.setColor(0xFFFFFFFF);
        int count = Math.max(8, model.decoCount);
        float step = w / (float) count;
        for (int i = 0; i < count; i++) {
            float left = i * step + step * 0.2f;
            float right = left + step * 0.4f;
            canvas.drawRect(left, 4, right, model.borderWidth, holes);
            canvas.drawRect(left, h - model.borderWidth, right, h - 4, holes);
        }
    }
}
