package com.example.imageeditorapp;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int SELECT_IMAGE = 1;
    private Button selectButton;
    private ImageView imageView;
    private DrawingView drawingView;
    private ImageButton currentPaint, drawBtn;
    private float smallBrush, mediumBrush, largeBrush;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selectButton = findViewById(R.id.selectButton);
        imageView = findViewById(R.id.imageView);
        drawingView = findViewById(R.id.drawingView);

        smallBrush = getResources().getInteger(R.integer.small_size);
        mediumBrush = getResources().getInteger(R.integer.medium_size);
        largeBrush = getResources().getInteger(R.integer.large_size);
        drawBtn = findViewById(R.id.brush_btn);
        drawBtn.setOnClickListener(this);

        LinearLayout paintLayout = findViewById(R.id.paint_colors);
        currentPaint = (ImageButton) paintLayout.getChildAt(0);
        currentPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // use implicit intent
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*"); //MIME type for images
                startActivityForResult(intent, SELECT_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == SELECT_IMAGE && resultCode == RESULT_OK){
            Uri dataUri = intent.getData();

            try {
                Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), dataUri);
                imageView.setImageBitmap(image);
                FrameLayout frame=(FrameLayout) findViewById(R.id.frameLayout);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, imageView.getHeight());
                frame.setLayoutParams(lp);
            } catch (IOException e) {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbuttons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void paintClicked(View view){
        if(view != currentPaint){
            // changes the color to the one the player just clicked
            ImageButton imgView = (ImageButton) view;
            String color = view.getTag().toString();
            System.out.println(color);
            drawingView.setColor(color);
            imgView.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currentPaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currentPaint = (ImageButton) view;
        }
    }

    @Override
    public void onClick(View view) {
        // If the user clicks on the brush button
        if(view.getId()==R.id.brush_btn){
            final Dialog brushDialog = new Dialog(this);
            brushDialog.setTitle("Brush Size");
        }
    }
}