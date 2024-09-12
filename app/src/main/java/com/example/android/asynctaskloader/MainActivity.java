package com.example.android.asynctaskloader;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.android.asynctaskloader.utilities.MovieDbHelper;

public class MainActivity extends AppCompatActivity {

    private EditText title, dateTime, scenario, realisation, music, critique;
    private Button btnSave, btnShare, btnWipe;
    private MovieDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        title = findViewById(R.id.title);
        dateTime = findViewById(R.id.date_time);
        scenario = findViewById(R.id.scenario);
        realisation = findViewById(R.id.realisation);
        music = findViewById(R.id.music);
        critique = findViewById(R.id.critique);

        btnSave = findViewById(R.id.btn_save);
        btnShare = findViewById(R.id.btn_share);
        btnWipe = findViewById(R.id.btn_wipe);

        dbHelper = new MovieDbHelper(this);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveReview();
            }
        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareReview();
            }
        });

        btnWipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wipeFields();
            }
        });

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateButtonStates();
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        title.addTextChangedListener(textWatcher);
        dateTime.addTextChangedListener(textWatcher);
        scenario.addTextChangedListener(textWatcher);
        realisation.addTextChangedListener(textWatcher);
        music.addTextChangedListener(textWatcher);
        critique.addTextChangedListener(textWatcher);

        updateButtonStates();
    }

    private void saveReview() {
        try {
            String savedTitle = title.getText().toString();
            String savedDateTime = dateTime.getText().toString();
            int savedScenario = Integer.parseInt(scenario.getText().toString());
            int savedRealisation = Integer.parseInt(realisation.getText().toString());
            int savedMusic = Integer.parseInt(music.getText().toString());
            String savedCritique = critique.getText().toString();

            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(MovieDbHelper.COLUMN_TITLE, savedTitle);
            values.put(MovieDbHelper.COLUMN_DATE_TIME, savedDateTime);
            values.put(MovieDbHelper.COLUMN_SCENARIO, savedScenario);
            values.put(MovieDbHelper.COLUMN_REALISATION, savedRealisation);
            values.put(MovieDbHelper.COLUMN_MUSIC, savedMusic);
            values.put(MovieDbHelper.COLUMN_CRITIQUE, savedCritique);

            long newRowId = db.insert(MovieDbHelper.TABLE_NAME, null, values);

            if (newRowId != -1) {
                Toast.makeText(this, "Critique sauvegardée!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erreur de sauvegarde", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Erreur: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void shareReview() {
        String emailBody = "Titre: " + title.getText().toString() + "\n" +
                "Date et Heure: " + dateTime.getText().toString() + "\n" +
                "Scénario: " + scenario.getText().toString() + "\n" +
                "Réalisation: " + realisation.getText().toString() + "\n" +
                "Musique: " + music.getText().toString() + "\n" +
                "Critique: " + critique.getText().toString();

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("plain/text");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Critique de Film");
        emailIntent.putExtra(Intent.EXTRA_TEXT, emailBody);

        startActivity(Intent.createChooser(emailIntent, "Envoyer par email..."));
    }

    private void wipeFields() {
        title.setText("");
        dateTime.setText("");
        scenario.setText("");
        realisation.setText("");
        music.setText("");
        critique.setText("");
    }

    private void updateButtonStates() {
        boolean isAllFieldsFilled = !title.getText().toString().isEmpty() &&
                !dateTime.getText().toString().isEmpty() &&
                !scenario.getText().toString().isEmpty() &&
                !realisation.getText().toString().isEmpty() &&
                !music.getText().toString().isEmpty() &&
                !critique.getText().toString().isEmpty();

        btnSave.setEnabled(isAllFieldsFilled);
        btnShare.setEnabled(isAllFieldsFilled);
    }
}
