package com.example.imageeditorapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class SettingsActivity extends AppCompatActivity {

    private ImageButton currentPaint;
    private MainActivity main = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        LinearLayout paintLayout = findViewById(R.id.paint_colors);
        currentPaint = (ImageButton) paintLayout.getChildAt(0);
        currentPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
    }

    public void paintClicked(View view){
        if(view != currentPaint){
            // changes the color to the one the player just clicked
            ImageButton imgView = (ImageButton) view;
            String color = view.getTag().toString();
            System.out.println(color);
            //main.setColor(color);
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currentPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currentPaint = (ImageButton) view;
        }
    }
}
