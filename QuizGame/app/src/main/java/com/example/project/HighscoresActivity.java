package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;

public class HighscoresActivity extends AppCompatActivity {
    private TextView highscoresTextView;
    private TableLayout highscoresTableLayout;
    private Button returnButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        highscoresTextView = findViewById(R.id.highscoresTextView);
        highscoresTableLayout = findViewById(R.id.highscoresTableLayout);
        returnButton = findViewById(R.id.returnButton);
        //adding button functionality
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HighscoresActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void loadComputerScienceHighscores(View view) {
        loadHighscores("ComputerScience");
    }

    public void loadMathsHighscores(View view) {
        loadHighscores("Maths");
    }

    public void loadGeographyHighscores(View view) {
        loadHighscores("Geography");
    }

    private void loadHighscores(String category) {
        MyDbHandler myDB = new MyDbHandler(this);
        List<String> highscores = myDB.getTop10HighscoresByCategory(category);

        // Clear existing rows
        highscoresTableLayout.removeAllViews();

        // Create and add rows dynamically
        for (String score : highscores) {
            String[] parts = score.split(": ");
            String name = parts[0];
            String scoreValue = parts[1];

            TableRow row = new TableRow(this);

            TextView nameTextView = new TextView(this);
            nameTextView.setText(name);
            nameTextView.setTextColor(getResources().getColor(android.R.color.white));
            nameTextView.setPadding(16, 16, 16, 16);

            TextView scoreTextView = new TextView(this);
            scoreTextView.setText(scoreValue);
            scoreTextView.setTextColor(getResources().getColor(android.R.color.white));
            scoreTextView.setPadding(16, 16, 16, 16);

            row.addView(nameTextView);
            row.addView(scoreTextView);

            highscoresTableLayout.addView(row);
        }
    }
}
