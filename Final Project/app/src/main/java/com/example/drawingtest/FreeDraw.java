package com.example.drawingtest;

import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.service.credentials.Action;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.ToggleButton;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Stack;


public class FreeDraw extends AppCompatActivity {

    private DrawingView drawingView;
    private LinearLayout sideMenu;
    private ToggleButton btnToggleMenu;

    private Button save;
    private Button undo;
    public Button clear;
    public Button color;
    public Button erase;
    private Button brush;
    private Button pencil;
    private Button bucket;
    private Button more;

    private MediaPlayer Music;

//    private static Stack<DrawingAction> drawingActions = new Stack<>();
//    private Stack<DrawingAction> redoActions = new Stack<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_draw);


        drawingView = findViewById(R.id.drawingView);
        sideMenu = findViewById(R.id.sideMenu);
        btnToggleMenu = findViewById(R.id.btnToggleMenu);



        setupMenuButtons();
        setupToggleButton();


        Music = MediaPlayer.create(this, R.raw.minigame1);
        Music.setLooping(true);
        Music.start();



    }

    public void setupMenuButtons() {  //set up all teh buttons
        save = findViewById(R.id.btnTool1);
        undo = findViewById(R.id.btnTool2);
        clear = findViewById(R.id.btnTool3);
        color = findViewById(R.id.btnTool4);
        erase = findViewById(R.id.btnTool5);
        brush = findViewById(R.id.btnTool6);
        pencil = findViewById(R.id.btnTool7);
        bucket = findViewById(R.id.btnTool8);
        more = findViewById(R.id.btnTool9);

        // Set undo history


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.saveCanvas();
                // Handle tool 1 click
            }
        });
        // Set up other button click listeners

        undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.undo();
            }
        });

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.clearCanvas();
            }
        });

        color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.showColorPicker(view);
            }
        });

        erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.showSizeDialog('e');
                // Handle tool 1 click
            }
        });


        brush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle tool 1 click
                drawingView.showSizeDialog('b');
            }
        });

        pencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.showSizeDialog('p');
                // Handle tool 1 click
            }
        });

        bucket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawingView.showSizeDialog('f');
                // Handle tool 1 click
            }
        });


        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle tool 1 click
            }
        });

        // Set up other button click listeners
}



    private void setupToggleButton() {
        btnToggleMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (btnToggleMenu.isChecked()) {
                    // Show the side menu
                    sideMenu.setVisibility(View.VISIBLE);
                } else {
                    // Hide the side menu
                    sideMenu.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Release resources when the activity is destroyed
        if (Music != null) {
            Music.release();
            Music = null;
        }
    }


}
