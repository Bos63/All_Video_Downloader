package com.example.myphotoeditor;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CustomEditorView extends View {

    private Bitmap baseBitmap;
    private TemplateModel templateModel;
    private final List<StickerLayer> stickerLayers = new ArrayList<>();
    private final List<TextLayer> textLayers = new ArrayList<>();

    private int selectedSticker = -1;
    private int selectedText = -1;
    private float lastX, lastY;
    private float lastRotationAngle = 0f;

    private final ScaleGestureDetector scaleGestureDetector;
    private final GestureDetector gestureDetector;

    public CustomEditorView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        scaleGestureDetector = new ScaleGestureDetector(context, new ScaleListener());
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (selectedSticker >= 0 && selectedSticker < stickerLayers.size()) {
                    stickerLayers.remove(selectedSticker);
                    selectedSticker = -1;
                    invalidate();
                    return true;
                }
                return super.onDoubleTap(e);
            }
        });
    }

    public void setBaseBitmap(Bitmap bitmap) {
        this.baseBitmap = bitmap;
        invalidate();
    }

    public void setTemplate(TemplateModel templateModel) {
        this.templateModel = templateModel;
        invalidate();
    }

    public void addSticker(StickerModel model) {
        stickerLayers.add(new StickerLayer(model, getWidth() / 2f, getHeight() / 2f, 120f, 0f, model.defaultColor));
        selectedSticker = stickerLayers.size() - 1;
        selectedText = -1;
        invalidate();
    }

    public void addText(String text) {
        textLayers.add(new TextLayer(text, getWidth() / 2f, getHeight() * 0.2f, Color.WHITE, 42f, false, false));
        selectedText = textLayers.size() - 1;
        selectedSticker = -1;
        invalidate();
    }

    public void setSelectedTextColor(int color) {
        if (selectedText >= 0 && selectedText < textLayers.size()) {
            textLayers.get(selectedText).color = color;
            invalidate();
        }
    }

    public void setSelectedTextSize(float sizePx) {
        if (selectedText >= 0 && selectedText < textLayers.size()) {
            textLayers.get(selectedText).size = sizePx;
            invalidate();
        }
    }

    public Bitmap exportBitmap() {
        Bitmap out = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(out);
        draw(canvas);
        return out;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(0xFF111111);

        if (baseBitmap != null) {
            RectF dst = new RectF(0, 0, getWidth(), getHeight());
            canvas.drawBitmap(baseBitmap, null, dst, null);
        }

        if (templateModel != null) {
            TemplateRenderer.drawTemplate(canvas, templateModel, getWidth(), getHeight());
        }

        for (int i = 0; i < stickerLayers.size(); i++) {
            StickerLayer s = stickerLayers.get(i);
            StickerRenderer.drawSticker(canvas, s.model, s.x, s.y, s.size, s.rotation, s.color);
            if (i == selectedSticker) {
                Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
                p.setStyle(Paint.Style.STROKE);
                p.setStrokeWidth(2f);
                p.setColor(Color.WHITE);
                canvas.drawCircle(s.x, s.y, s.size * 0.75f, p);
            }
        }

        for (int i = 0; i < textLayers.size(); i++) {
            TextLayer t = textLayers.get(i);
            TextPaint paint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(t.color);
            paint.setTextSize(t.size);
            paint.setFakeBoldText(t.bold);
            paint.setTextSkewX(t.italic ? -0.2f : 0f);
            paint.setTextAlign(Paint.Align.CENTER);
            canvas.drawText(t.text, t.x, t.y, paint);
            if (i == selectedText) {
                Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
                p.setStyle(Paint.Style.STROKE);
                p.setColor(Color.WHITE);
                p.setStrokeWidth(2f);
                canvas.drawRect(t.x - 120, t.y - t.size, t.x + 120, t.y + 20, p);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        scaleGestureDetector.onTouchEvent(event);

        if (event.getPointerCount() == 2 && selectedSticker >= 0) {
            float angle = angleBetweenLines(event);
            if (event.getActionMasked() == MotionEvent.ACTION_POINTER_DOWN) {
                lastRotationAngle = angle;
            } else if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
                float delta = angle - lastRotationAngle;
                stickerLayers.get(selectedSticker).rotation += delta;
                lastRotationAngle = angle;
                invalidate();
            }
        }

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                lastX = event.getX();
                lastY = event.getY();
                pickLayer(lastX, lastY);
                return true;
            case MotionEvent.ACTION_MOVE:
                float dx = event.getX() - lastX;
                float dy = event.getY() - lastY;
                lastX = event.getX();
                lastY = event.getY();
                if (selectedSticker >= 0 && selectedSticker < stickerLayers.size()) {
                    StickerLayer s = stickerLayers.get(selectedSticker);
                    s.x += dx;
                    s.y += dy;
                    invalidate();
                } else if (selectedText >= 0 && selectedText < textLayers.size()) {
                    TextLayer t = textLayers.get(selectedText);
                    t.x += dx;
                    t.y += dy;
                    invalidate();
                }
                return true;
        }
        return super.onTouchEvent(event);
    }

    private void pickLayer(float x, float y) {
        selectedSticker = -1;
        selectedText = -1;

        for (int i = stickerLayers.size() - 1; i >= 0; i--) {
            StickerLayer s = stickerLayers.get(i);
            float dx = x - s.x;
            float dy = y - s.y;
            if (Math.sqrt(dx * dx + dy * dy) <= s.size) {
                selectedSticker = i;
                invalidate();
                return;
            }
        }

        for (int i = textLayers.size() - 1; i >= 0; i--) {
            TextLayer t = textLayers.get(i);
            if (Math.abs(x - t.x) < 130 && Math.abs(y - t.y) < 70) {
                selectedText = i;
                invalidate();
                return;
            }
        }
        invalidate();
    }

    private float angleBetweenLines(MotionEvent event) {
        float dx = event.getX(0) - event.getX(1);
        float dy = event.getY(0) - event.getY(1);
        return (float) Math.toDegrees(Math.atan2(dy, dx));
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            if (selectedSticker >= 0 && selectedSticker < stickerLayers.size()) {
                StickerLayer s = stickerLayers.get(selectedSticker);
                s.size = Math.max(40f, Math.min(420f, s.size * detector.getScaleFactor()));
                invalidate();
                return true;
            }
            if (selectedText >= 0 && selectedText < textLayers.size()) {
                TextLayer t = textLayers.get(selectedText);
                t.size = Math.max(22f, Math.min(140f, t.size * detector.getScaleFactor()));
                invalidate();
                return true;
            }
            return false;
        }
    }

    private static class StickerLayer {
        final StickerModel model;
        float x;
        float y;
        float size;
        float rotation;
        int color;

        StickerLayer(StickerModel model, float x, float y, float size, float rotation, int color) {
            this.model = model;
            this.x = x;
            this.y = y;
            this.size = size;
            this.rotation = rotation;
            this.color = color;
        }
    }

    private static class TextLayer {
        String text;
        float x;
        float y;
        int color;
        float size;
        boolean bold;
        boolean italic;

        TextLayer(String text, float x, float y, int color, float size, boolean bold, boolean italic) {
            this.text = text;
            this.x = x;
            this.y = y;
            this.color = color;
            this.size = size;
            this.bold = bold;
            this.italic = italic;
        }
    }
}
