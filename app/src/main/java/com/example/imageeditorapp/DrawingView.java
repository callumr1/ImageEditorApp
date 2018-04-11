package com.example.imageeditorapp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jc429141 on 11/04/2018.
 */

public class DrawingView extends View {
    public DrawingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing() {
        //creates the drawing area which the user will interact with
    }
}
