package com.example.imageeditorapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.TypedValue;

/**
 * Created by Callum on 11/04/2018.
 *
 * This class allows the player to draw on the image canvas.
 */

public class DrawingView extends View {

    private Path drawPath;
    private Paint drawPaint, canvasPaint;
    private int paintColor = getResources().getColor(R.color.Black);
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;
    private Float brushSize, lastBrushSize;
    private boolean erase = false;

    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing() {
        // initiates the brush size
        brushSize = Float.valueOf(getResources().getInteger(R.integer.medium_size));
        lastBrushSize = brushSize;

        //creates the drawing area which the user will interact with
        drawPath = new Path();
        drawPaint = new Paint();

        // initialise the path properties
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(brushSize);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();

        switch(event.getAction()){
            // users touches down on the screen
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            // user moves their finger
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                break;
            // user lifts their finger off the screen
            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;
            default:
                return false;
        }
        invalidate();
        return true;
    }

    public void setColor(String newColor){
        System.out.println("Called");
        invalidate();
        paintColor = Color.parseColor(newColor);
        drawPaint.setColor(paintColor);
    }

    public void setBrushSize(float newSize){
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newSize, getResources().getDisplayMetrics());
        brushSize = pixelAmount;
        drawPaint.setStrokeWidth(brushSize);
    }

    public void setLastBrushSize(float lastSize){
        lastBrushSize = lastSize;
    }

    public float getLastBrushSize(){
        return lastBrushSize;
    }

    public void setErase(boolean isErase){
        // sets erase to either true or false
        erase = isErase;

        if (erase){
            drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        }
        else{
            drawPaint.setXfermode(null);
        }
    }
}
