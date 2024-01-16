package com.example.drawingtest;

// com.example.drawingtest.DrawingView

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.PopupWindow;
import android.widget.SeekBar;

import java.util.ArrayList;
import java.util.Stack;

public class DrawingView extends View implements View.OnTouchListener {

    private Path drawPath;
    private AlertDialog sizeDialog;

    private static final float TOUCH_TOLERANCE = 4;
    private float mX, mY;
    private Path mPath;
    private final ArrayList<Stroke> paths = new ArrayList<>();
    private int currentColor;
    private int strokeWidth;
    public static Paint drawPaint;
    private Paint canvasPaint;
    private Canvas drawCanvas;
    private Bitmap canvasBitmap;

    public int historyCount;

    private static final CGPointArray userPoints = new CGPointArray();
    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    private void setupDrawing() {
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setColor(Color.BLACK);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(15);
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
        canvas.save();
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float touchX = event.getX();
        float touchY = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                drawPath.moveTo(touchX, touchY);
                break;
            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(touchX, touchY);
                userPoints.add(new CGPoint(touchX, touchY));
                break;
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

    public static void setColor(int color) {
        drawPaint.setColor(color);
    }

    public void setBrushSize(float size) {
        drawPaint.setStrokeWidth(size);
    }

    public void clearCanvas() {
        drawCanvas.drawColor(Color.WHITE);
        invalidate();
        drawCanvas.restoreToCount(1);
    }

    public void autoSave(){
        drawCanvas.save();
//        drawCanvas.getSaveCount();
    }

    public Bitmap saveCanvas(){
       return canvasBitmap;


    }

    public void undo() {
        // Check if there are any strokes to undo.
        if (paths.size() != 0) {
            paths.remove(paths.size() - 1);
            invalidate();
        }        }



    public void showColorPicker(View anchorView) {
        // Inflate the color picker layout
        View colorPickerView =
                LayoutInflater.from(getContext()).inflate(R.layout.color_picker_layout, null);

        // Set up the PopupWindow
        final PopupWindow popupWindow = new PopupWindow(
                colorPickerView,
                GridLayout.LayoutParams.WRAP_CONTENT,
                GridLayout.LayoutParams.WRAP_CONTENT,
                true
        );
        popupWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                // Handle color picker dismissed
            }
        });

        // Set up color buttons
        Button red = colorPickerView.findViewById(R.id.red);
        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle color selection, e.g., set color for your tool
                int selectedColor = ((ColorDrawable) red.getBackground()).getColor();
                DrawingView.setColor(selectedColor);
                popupWindow.dismiss();
            }
        });

        Button orange = colorPickerView.findViewById(R.id.orange);
        orange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle color selection, e.g., set color for your tool
                int selectedColor = ((ColorDrawable) orange.getBackground()).getColor();
                DrawingView.setColor(selectedColor);
                popupWindow.dismiss();
            }
        });

        Button yellow = colorPickerView.findViewById(R.id.yellow);
        yellow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle color selection, e.g., set color for your tool
                int selectedColor = ((ColorDrawable) yellow.getBackground()).getColor();
                DrawingView.setColor(selectedColor);
                popupWindow.dismiss();
            }
        });

        Button green = colorPickerView.findViewById(R.id.green);
        green.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle color selection, e.g., set color for your tool
                int selectedColor = ((ColorDrawable) green.getBackground()).getColor();
                DrawingView.setColor(selectedColor);
                popupWindow.dismiss();
            }
        });

        Button lime = colorPickerView.findViewById(R.id.lime);
        lime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle color selection, e.g., set color for your tool
                int selectedColor = ((ColorDrawable) lime.getBackground()).getColor();
                DrawingView.setColor(selectedColor);
                popupWindow.dismiss();
            }
        });

        Button blue = colorPickerView.findViewById(R.id.blue);
        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle color selection, e.g., set color for your tool
                int selectedColor = ((ColorDrawable) blue.getBackground()).getColor();
                DrawingView.setColor(selectedColor);
                popupWindow.dismiss();
            }
        });

        Button lightblue = colorPickerView.findViewById(R.id.lightblue);
        lightblue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle color selection, e.g., set color for your tool
                int selectedColor = ((ColorDrawable) lightblue.getBackground()).getColor();
                DrawingView.setColor(selectedColor);
                popupWindow.dismiss();
            }
        });

        Button violet = colorPickerView.findViewById(R.id.violet);
        violet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle color selection, e.g., set color for your tool
                int selectedColor = ((ColorDrawable) violet.getBackground()).getColor();
                DrawingView.setColor(selectedColor);
                popupWindow.dismiss();
            }
        });

        Button brown = colorPickerView.findViewById(R.id.brown);
        brown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle color selection, e.g., set color for your tool
                int selectedColor = ((ColorDrawable) brown.getBackground()).getColor();
                DrawingView.setColor(selectedColor);
                popupWindow.dismiss();
            }
        });

        Button pink = colorPickerView.findViewById(R.id.pink);
        pink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle color selection, e.g., set color for your tool
                int selectedColor = ((ColorDrawable) pink.getBackground()).getColor();
                DrawingView.setColor(selectedColor);
                popupWindow.dismiss();
            }
        });

        Button white = colorPickerView.findViewById(R.id.white);
        white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle color selection, e.g., set color for your tool
                int selectedColor = ((ColorDrawable) white.getBackground()).getColor();
                DrawingView.setColor(selectedColor);
                popupWindow.dismiss();
            }
        });


        Button gray = colorPickerView.findViewById(R.id.gray);
        gray.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle color selection, e.g., set color for your tool
                int selectedColor = ((ColorDrawable) gray.getBackground()).getColor();
                DrawingView.setColor(selectedColor);
                popupWindow.dismiss();
            }
        });

        Button black = colorPickerView.findViewById(R.id.black);
        black.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle color selection, e.g., set color for your tool
                int selectedColor = ((ColorDrawable) black.getBackground()).getColor();
                DrawingView.setColor(selectedColor);
                popupWindow.dismiss();
            }
        });

        // Add more color buttons and their click listeners as needed

        // Show the PopupWindow
        popupWindow.showAsDropDown(anchorView);
    }

    public void showSizeDialog(final char tool) {
        // Inflate the layout for the dialog
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.size_dialog_layout, null);

        // Set up the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        builder.setTitle("Select Size");

        final SeekBar sizeSeekBar = dialogView.findViewById(R.id.sizeSeekBar);

        // Set up the OK button click listener
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                float selectedSize = sizeSeekBar.getProgress() + 1; // Add 1 to avoid size 0
                handleSizeSelection(selectedSize, tool);
                dialog.dismiss();
            }
        });

        // Set up the Cancel button click listener
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Create and show the dialog
        sizeDialog = builder.create();
        sizeDialog.show();

    }
    private void handleSizeSelection(float selectedSize, char tool) {
        setBrushSize(selectedSize);

        switch (tool) {
            case 'b':
                toBrush();
                break;
            case 'p':
                toPencil();
                break;
            case 'e':
                toEraser();
                break;

            // Handle other tools as needed
        }
    }

    private void toBrush() {
        drawPaint.setAntiAlias(true);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    private void toPencil() {
        drawPaint.setAntiAlias(false);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.BEVEL);
        drawPaint.setStrokeCap(Paint.Cap.SQUARE);
    }


    private void toEraser(){
        drawPaint.setAntiAlias(true);
        sizeChanger();
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        // Set the color of the eraser to the canvas color.
        drawPaint.setColor(Color.WHITE);
    }

    public static void toBucket(Bitmap bitmap, MotionEvent event,
                                int targetColor, int replacementColor) {
        // Create a stack to store the coordinates of the pixels that need to be processed.
        Stack<Point> stack = new Stack<>();

        // Add the starting point to the stack.

        float touchX = event.getX();
        float touchY = event.getY();


        stack.push(new Point((int) touchX, (int) touchY));

        // While the stack is not empty, process the next pixel.
        while (!stack.isEmpty()) {
            // Get the next pixel from the stack.
            Point point = stack.pop();

            // Get the color of the pixel.
            int color = bitmap.getPixel(point.x, point.y);

            // If the color of the pixel is the target color, replace it with the replacement color.
            if (color == targetColor) {
                bitmap.setPixel(point.x, point.y, replacementColor);

                // Add the neighboring pixels to the stack.
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        if (i != 0 || j != 0) {
                            int newX = point.x + i;
                            int newY = point.y + j;

                            if (newX >= 0 && newX < bitmap.getWidth() && newY >= 0 && newY < bitmap.getHeight()) {
                                int newColor = bitmap.getPixel(newX, newY);

                                if (newColor == targetColor) {
                                    stack.push(new Point(newX, newY));
                                }
                            }
                        }
                    }
                }
            }
        }

    }

    public void sizeChanger(){
        setBrushSize(10);
    }

    /**
     * Called when a touch event is dispatched to a view. This allows listeners to
     * get a chance to respond before the target view.
     *
     * @param v     The view the touch event has been dispatched to.
     * @param event The MotionEvent object containing full information about
     *              the event.
     * @return True if the listener has consumed the event, false otherwise.
     */
//    @Override
//    public boolean onTouch(View v, MotionEvent event) {
//        float touchX = event.getX();
//        float touchY = event.getY();
//
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//            case MotionEvent.ACTION_MOVE:
//
//                // Optionally, you can do something with the points here as the user is drawing
//                invalidate(); // Redraw the view if needed
//                break;
//            case MotionEvent.ACTION_UP:
//                // Handle touch up event if needed
//                break;
//        }
//
//        return true; // Consume the touch event
//    }

    public static CGPointArray createUserInput() {
        // Return the collected user points
        return userPoints;
    }



}
