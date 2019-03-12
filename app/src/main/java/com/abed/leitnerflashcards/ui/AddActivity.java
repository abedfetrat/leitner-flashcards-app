package com.abed.leitnerflashcards.ui;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.abed.leitnerflashcards.R;
import com.abed.leitnerflashcards.data.Card;
import com.abed.leitnerflashcards.data.FileManager;
import com.abed.leitnerflashcards.data.Repository;
import com.abed.leitnerflashcards.ui.MainActivity;
import com.abed.leitnerflashcards.utils.DateUtil;

import java.io.InputStream;
import java.util.Calendar;

public class AddActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int PICK_IMAGE = 0;
    private static final int PICK_FRONT_AUDIO = 1;
    private static final int PICK_BACK_AUDIO = 2;

    private Uri pickedImageUri;
    private Uri pickedFrontAudioUri;
    private Uri pickedBackAudioUri;

    private RadioGroup radioGroup;
    private EditText etFrontText;
    private EditText etBackText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        radioGroup = findViewById(R.id.rg);
        Button btnPickImage = findViewById(R.id.btnImage);
        etFrontText = findViewById(R.id.etText1);
        Button btnPickFrontAudio = findViewById(R.id.btnAudio1);
        etBackText = findViewById(R.id.etText2);
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
            save();
            finish();
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                switch (requestCode) {
                    case PICK_IMAGE:
                        pickedImageUri = uri;
                        break;
                    case PICK_FRONT_AUDIO:
                        pickedFrontAudioUri = uri;
                        break;
                    case PICK_BACK_AUDIO:
                        pickedBackAudioUri = uri;
                        break;
                }
            }
        }
    }

    private void openPicker(String type, String title, int reqCode) {
        Intent intent = new Intent();
        intent.setType(type);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, title), reqCode);
    }

    private void save() {
        String imageFilePath = null;
        String frontAudioFilePath = null;
        String backAudioFilePath = null;

        if (pickedImageUri != null) {
            imageFilePath = FileManager.getInstance(this).saveFile(getContentResolver(), pickedImageUri);
        }

        if (pickedFrontAudioUri != null) {
            frontAudioFilePath = FileManager.getInstance(this).saveFile(getContentResolver(), pickedFrontAudioUri);
        }

        if (pickedBackAudioUri != null) {
            backAudioFilePath = FileManager.getInstance(this).saveFile(getContentResolver(), pickedBackAudioUri);
        }

        // create card object
        String frontText = etFrontText.getText().toString();
        String backText = etBackText.getText().toString();

        Calendar dueDate;
        if (radioGroup.getCheckedRadioButtonId() == R.id.rb_today)
            dueDate = DateUtil.getCalendarWithoutTime();
        else
            dueDate = DateUtil.getCalendarPlusDays(1);

        Card card = new Card(dueDate, imageFilePath, frontText, frontAudioFilePath, backText, backAudioFilePath);

        // save card to database
        Repository.getInstance(getApplication()).insertCard(card);
    }
}
