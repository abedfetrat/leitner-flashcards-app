package com.abed.leitnerflashcards;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int PICK_IMAGE = 0;
    private static final int PICK_FRONT_AUDIO = 1;
    private static final int PICK_BACK_AUDIO = 2;

    private String pickedImageUri;
    private String pickedFrontAudioUri;
    private String pickedBackAudioUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        Button btnPickImage = findViewById(R.id.btnImage);
        EditText etFrontText = findViewById(R.id.etText1);
        Button btnPickFrontAudio = findViewById(R.id.btnAudio1);
        EditText etBackText = findViewById(R.id.etText2);
        Button btnPickBackAudio = findViewById(R.id.btnAudio2);

        btnPickImage.setOnClickListener((View v) -> {
            openPicker("image/*", "Pick image", PICK_IMAGE);

        });

        btnPickFrontAudio.setOnClickListener((View v) -> {
            openPicker("audio/*", "Pick front audio", PICK_FRONT_AUDIO);
        });

        btnPickBackAudio.setOnClickListener((View v) -> {
            openPicker("audio/*", "Pick back audio", PICK_BACK_AUDIO);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save) {
            // save logic
            finish();
        }

        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            switch (requestCode) {
                case PICK_IMAGE:
                    pickedImageUri = data.getDataString();
                    break;
                case PICK_FRONT_AUDIO:
                    pickedFrontAudioUri = data.getDataString();
                    break;
                case PICK_BACK_AUDIO:
                    pickedBackAudioUri = data.getDataString();
                    break;
            }
            Log.i(TAG, "picked content: " + data.getDataString());
        }
    }

    private void openPicker(String type, String title, int reqCode) {
        Intent intent = new Intent();
        intent.setType(type);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, title), reqCode);
    }
}
