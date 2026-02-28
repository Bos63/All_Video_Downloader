package com.example.myphotoeditor;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;

public class StickerRenderer {

    public static Bitmap createThumbnail(StickerModel model, int w, int h) {
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bmp);
        Paint bg = new Paint(Paint.ANTI_ALIAS_FLAG);
        bg.setColor(0xFF303030);
        canvas.drawRect(0, 0, w, h, bg);
        drawSticker(canvas, model, w / 2f, h / 2f, Math.min(w, h) * 0.35f, 0f, model.defaultColor);
        return bmp;
    }

    public static void drawSticker(Canvas canvas, StickerModel model, float cx, float cy, float size, float rotation, int color) {
        if (model == null) return;

        canvas.save();
        canvas.translate(cx, cy);
        canvas.rotate(rotation);

        Paint fill = new Paint(Paint.ANTI_ALIAS_FLAG);
        fill.setStyle(Paint.Style.FILL);
        fill.setColor(color);

        Paint stroke = new Paint(Paint.ANTI_ALIAS_FLAG);
        stroke.setStyle(Paint.Style.STROKE);
        stroke.setStrokeWidth(Math.max(2f, size * 0.08f));
        stroke.setColor(0xFFFFFFFF);

        switch (model.type) {
            case "HEART": drawHeart(canvas, size, fill); break;
            case "LIKE": drawLike(canvas, size, fill); break;
            case "STAR": drawStar(canvas, size, fill); break;
            case "SMILE": drawSmile(canvas, size, fill, stroke); break;
            case "CHAT": drawChat(canvas, size, fill); break;
            case "SHARE": drawShare(canvas, size, stroke); break;
            case "FIRE": drawFire(canvas, size, fill); break;
            case "GIFT": drawGift(canvas, size, fill, stroke); break;
            case "CLAP": drawClap(canvas, size, fill); break;
            case "BUBBLE": drawBubble(canvas, size, fill); break;
            case "ARROW": drawArrow(canvas, size, fill); break;
            case "CIRCLE": canvas.drawCircle(0, 0, size * 0.6f, fill); break;
            case "TRIANGLE": drawTriangle(canvas, size, fill); break;
            case "WAVE": drawWave(canvas, size, stroke); break;
            case "HEX": drawHex(canvas, size, stroke); break;
            case "DIAMOND": drawDiamond(canvas, size, fill); break;
            case "BADGE_SALE": drawBadge(canvas, size, fill, "SALE"); break;
            case "BADGE_NEW": drawBadge(canvas, size, fill, "NEW"); break;
            case "BADGE_BEST": drawBadge(canvas, size, fill, "BEST"); break;
            case "BADGE_WOW": drawBadge(canvas, size, fill, "WOW"); break;
            case "BADGE_TOP": drawBadge(canvas, size, fill, "TOP"); break;
            case "BADGE_PRO": drawBadge(canvas, size, fill, "PRO"); break;
            case "CAMERA": drawCamera(canvas, size, stroke, fill); break;
            case "MUSIC": drawMusic(canvas, size, stroke); break;
            case "PIN": drawPin(canvas, size, fill); break;
            case "CROWN": drawCrown(canvas, size, fill); break;
            case "BOLT": drawBolt(canvas, size, fill); break;
            case "MOON": drawMoon(canvas, size, fill); break;
            case "SUN": drawSun(canvas, size, stroke); break;
            case "LEAF": drawLeaf(canvas, size, fill); break;
        }

        canvas.restore();
    }

    private static void drawHeart(Canvas c, float s, Paint p){ Path path=new Path(); path.moveTo(0,s*0.45f); path.cubicTo(s*0.7f,-s*0.1f,s*0.55f,-s*0.8f,0,-s*0.35f); path.cubicTo(-s*0.55f,-s*0.8f,-s*0.7f,-s*0.1f,0,s*0.45f); c.drawPath(path,p);}    
    private static void drawLike(Canvas c,float s,Paint p){RectF r=new RectF(-s*0.45f,-s*0.1f,s*0.35f,s*0.45f); c.drawRoundRect(r,s*0.1f,s*0.1f,p); c.drawRect(-s*0.15f,-s*0.55f,s*0.15f,-s*0.05f,p);}    
    private static void drawStar(Canvas c,float s,Paint p){Path path=new Path(); for(int i=0;i<10;i++){double a=Math.toRadians(-90+i*36); float r=(i%2==0)?s*0.6f:s*0.25f; float x=(float)(Math.cos(a)*r), y=(float)(Math.sin(a)*r); if(i==0) path.moveTo(x,y); else path.lineTo(x,y);} path.close(); c.drawPath(path,p);}    
    private static void drawSmile(Canvas c,float s,Paint fill,Paint stroke){c.drawCircle(0,0,s*0.58f,fill); Paint eye=new Paint(Paint.ANTI_ALIAS_FLAG); eye.setColor(0xFF000000); c.drawCircle(-s*0.2f,-s*0.12f,s*0.06f,eye); c.drawCircle(s*0.2f,-s*0.12f,s*0.06f,eye); c.drawArc(new RectF(-s*0.25f,-s*0.1f,s*0.25f,s*0.3f),20,140,false,stroke);}    
    private static void drawChat(Canvas c,float s,Paint p){Path path=new Path(); path.addRoundRect(new RectF(-s*0.55f,-s*0.35f,s*0.55f,s*0.35f),s*0.2f,s*0.2f,Path.Direction.CW); path.moveTo(-s*0.1f,s*0.35f); path.lineTo(s*0.05f,s*0.55f); path.lineTo(s*0.2f,s*0.35f); c.drawPath(path,p);}    
    private static void drawShare(Canvas c,float s,Paint p){c.drawLine(-s*0.3f,0,s*0.2f,-s*0.25f,p); c.drawLine(-s*0.3f,0,s*0.2f,s*0.25f,p); c.drawCircle(-s*0.35f,0,s*0.1f,p); c.drawCircle(s*0.25f,-s*0.3f,s*0.1f,p); c.drawCircle(s*0.25f,s*0.3f,s*0.1f,p);}    
    private static void drawFire(Canvas c,float s,Paint p){Path path=new Path(); path.moveTo(0,-s*0.6f); path.cubicTo(s*0.4f,-s*0.1f,s*0.35f,s*0.45f,0,s*0.55f); path.cubicTo(-s*0.35f,s*0.45f,-s*0.4f,-s*0.1f,0,-s*0.6f); c.drawPath(path,p);}    
    private static void drawGift(Canvas c,float s,Paint fill,Paint stroke){c.drawRect(-s*0.5f,-s*0.1f,s*0.5f,s*0.45f,fill); c.drawRect(-s*0.5f,-s*0.35f,s*0.5f,-s*0.1f,fill); c.drawLine(0,-s*0.35f,0,s*0.45f,stroke); c.drawLine(-s*0.5f,0,s*0.5f,0,stroke);}    
    private static void drawClap(Canvas c,float s,Paint p){c.drawRoundRect(new RectF(-s*0.35f,-s*0.45f,s*0.2f,s*0.3f),s*0.1f,s*0.1f,p); c.drawCircle(s*0.25f,-s*0.2f,s*0.16f,p);}    
    private static void drawBubble(Canvas c,float s,Paint p){c.drawCircle(0,0,s*0.45f,p); Paint p2=new Paint(Paint.ANTI_ALIAS_FLAG); p2.setColor(0x66FFFFFF); c.drawCircle(-s*0.15f,-s*0.15f,s*0.12f,p2);}    
    private static void drawArrow(Canvas c,float s,Paint p){Path path=new Path(); path.moveTo(-s*0.5f,0); path.lineTo(s*0.15f,0); path.lineTo(s*0.15f,-s*0.25f); path.lineTo(s*0.55f,0); path.lineTo(s*0.15f,s*0.25f); path.lineTo(s*0.15f,0); path.close(); c.drawPath(path,p);}    
    private static void drawTriangle(Canvas c,float s,Paint p){Path path=new Path(); path.moveTo(0,-s*0.6f); path.lineTo(s*0.55f,s*0.45f); path.lineTo(-s*0.55f,s*0.45f); path.close(); c.drawPath(path,p);}    
    private static void drawWave(Canvas c,float s,Paint p){Path path=new Path(); path.moveTo(-s*0.6f,0); path.cubicTo(-s*0.3f,-s*0.25f,0,s*0.25f,s*0.3f,0); path.cubicTo(s*0.45f,-s*0.15f,s*0.55f,0,s*0.6f,0); c.drawPath(path,p);}    
    private static void drawHex(Canvas c,float s,Paint p){Path path=new Path(); for(int i=0;i<6;i++){double a=Math.toRadians(60*i); float x=(float)(Math.cos(a)*s*0.5f), y=(float)(Math.sin(a)*s*0.5f); if(i==0) path.moveTo(x,y); else path.lineTo(x,y);} path.close(); c.drawPath(path,p);}    
    private static void drawDiamond(Canvas c,float s,Paint p){Path path=new Path(); path.moveTo(0,-s*0.6f); path.lineTo(s*0.5f,0); path.lineTo(0,s*0.6f); path.lineTo(-s*0.5f,0); path.close(); c.drawPath(path,p);}    
    private static void drawBadge(Canvas c,float s,Paint p,String text){c.drawRoundRect(new RectF(-s*0.55f,-s*0.25f,s*0.55f,s*0.25f),s*0.15f,s*0.15f,p); Paint t=new Paint(Paint.ANTI_ALIAS_FLAG); t.setColor(0xFFFFFFFF); t.setTextSize(s*0.26f); t.setFakeBoldText(true); t.setTextAlign(Paint.Align.CENTER); c.drawText(text,0,s*0.1f,t);}    
    private static void drawCamera(Canvas c,float s,Paint stroke,Paint fill){c.drawRoundRect(new RectF(-s*0.55f,-s*0.3f,s*0.55f,s*0.35f),s*0.1f,s*0.1f,stroke); c.drawCircle(0,0,s*0.18f,stroke); c.drawRect(-s*0.3f,-s*0.45f,s*0.05f,-s*0.3f,fill);}    
    private static void drawMusic(Canvas c,float s,Paint p){c.drawLine(0,-s*0.45f,0,s*0.2f,p); c.drawLine(0,-s*0.45f,s*0.3f,-s*0.35f,p); c.drawCircle(-s*0.05f,s*0.3f,s*0.12f,p); c.drawCircle(s*0.25f,s*0.2f,s*0.12f,p);}    
    private static void drawPin(Canvas c,float s,Paint p){Path path=new Path(); path.addCircle(0,-s*0.1f,s*0.25f,Path.Direction.CW); path.moveTo(0,s*0.55f); path.lineTo(-s*0.2f,s*0.05f); path.lineTo(s*0.2f,s*0.05f); path.close(); c.drawPath(path,p);}    
    private static void drawCrown(Canvas c,float s,Paint p){Path path=new Path(); path.moveTo(-s*0.55f,s*0.25f); path.lineTo(-s*0.35f,-s*0.2f); path.lineTo(0,s*0.1f); path.lineTo(s*0.35f,-s*0.2f); path.lineTo(s*0.55f,s*0.25f); path.close(); c.drawPath(path,p);}    
    private static void drawBolt(Canvas c,float s,Paint p){Path path=new Path(); path.moveTo(-s*0.1f,-s*0.6f); path.lineTo(s*0.2f,-s*0.1f); path.lineTo(0,-s*0.1f); path.lineTo(s*0.1f,s*0.6f); path.lineTo(-s*0.2f,0); path.lineTo(0,0); path.close(); c.drawPath(path,p);}    
    private static void drawMoon(Canvas c,float s,Paint p){c.drawCircle(-s*0.05f,0,s*0.45f,p); Paint cut=new Paint(Paint.ANTI_ALIAS_FLAG); cut.setColor(0xFF141414); c.drawCircle(s*0.15f,-s*0.05f,s*0.45f,cut);}    
    private static void drawSun(Canvas c,float s,Paint p){c.drawCircle(0,0,s*0.25f,p); for(int i=0;i<8;i++){double a=Math.toRadians(i*45); c.drawLine((float)Math.cos(a)*s*0.35f,(float)Math.sin(a)*s*0.35f,(float)Math.cos(a)*s*0.58f,(float)Math.sin(a)*s*0.58f,p);}}    
    private static void drawLeaf(Canvas c,float s,Paint p){Path path=new Path(); path.moveTo(0,-s*0.5f); path.cubicTo(s*0.55f,-s*0.2f,s*0.4f,s*0.45f,0,s*0.55f); path.cubicTo(-s*0.4f,s*0.45f,-s*0.55f,-s*0.2f,0,-s*0.5f); c.drawPath(path,p);}    
}
