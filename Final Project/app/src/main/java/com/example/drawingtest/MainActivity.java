package com.example.drawingtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.media.SoundPool;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private MediaPlayer Music;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Music = MediaPlayer.create(this, R.raw.menu);
        Music.setLooping(true);
        Music.start();











    }
    // Function to show the game mode dialog
    private void showGameModeDialog(String title, String description) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(description)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Do something on OK click if needed
                    }
                });

        // Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu); // Create a menu layout file (main_menu.xml)
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        int itemId = item.getItemId();
        if (itemId == R.id.menu_free_style) {
            if (Music != null) {
                Music.release();
                Music = null;
            }
            startActivity(new Intent(this, FreeDraw.class));
            return true;
        } else if (itemId == R.id.menu_options || itemId == R.id.menu_gear_icon) {
            if (Music != null) {
                Music.release();
                Music = null;
            }
            startActivity(new Intent(this, Options.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    // Click event for the images
    public void onImageClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.ivGuess) {// Handle click for image 1
            showToast("Coming Soon");
        } else if (viewId == R.id.ivCollab) {// Handle click for image 2
            showToast("Coming Soon");
        } else if (viewId == R.id.ivBlind) {// Handle click for image 3
            showToast("Coming Soon");
        } else if (viewId == R.id.ivTrace) {// Handle click for image 4
            startActivity(new Intent(this, ImgRecognitionTest.class));
            showToast("Echo Sketch Clicked");


            // Add more cases for other images as needed
        }

    }

    // Click event for the "Rules" buttons
    public void onRulesButtonClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.btpp) {// Handle click for image 1
            showGameModeDialog("Game Mode 1: Palette Panic!", "In Palette Panic, the goal is to " +
                    "guess what the current artist is drawing within a certain time limit. The " +
                    "player to guess correctly first wins!");
        } else if (viewId == R.id.btcollab) {// Handle click for image 2
            showGameModeDialog("Game Mode 2: Dye-Lemma!", "Dye-lemma! is a collaborative game " +
                    "where the goal is to draw the item given by the prompt before the time limit" +
                    ". The catch is that each team of players has their own canvas and time " +
                    "limit.");
        } else if (viewId == R.id.btblind) {// Handle click for image 3
            showGameModeDialog("Game Mode 3: Ink-clipse", "The lights are out in Ink-clipse! In " +
                    "this game mode the player is given a prompt and told to draw it, pretty " +
                    "simple. The catch is that you cannot see as you're drawing and the canvas is" +
                    " only revealed after the player presses finish or the time runs out.");
        } else if (viewId == R.id.bttrace) {// Handle click for image 4
            showGameModeDialog("Game Mode 4: Echo Sketch", "Echo Sketch is a proof of concept " +
                    "game where over the course of 3 rounds, the player is given a shape to draw." +
                    " The player must then accurately draw the given shape to the best of their " +
                    "abilities without lifting their finger. The player is then given a guess as " +
                    "to what they tried to draw, as well as a score and the time it took them to " +
                    "draw it.");
            // Add more cases for other images as needed
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
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

    // This method is called when the options menu is created.

}
