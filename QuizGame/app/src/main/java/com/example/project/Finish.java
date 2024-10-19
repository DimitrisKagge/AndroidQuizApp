package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

public class Finish extends AppCompatActivity {
    EditText name_input;
    Button add;
    int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_finish);

        String category = getIntent().getStringExtra("Category");
        score = getIntent().getIntExtra("Score", 0); // Default score is 0 if not passed
        name_input = findViewById(R.id.editT);
        add = findViewById(R.id.button);
        //adding button functionality and getting the name from the editext
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDbHandler myDB = new MyDbHandler(Finish.this);
                boolean isInserted = myDB.addPlayer(name_input.getText().toString().trim(), score, category);
                if (!isInserted) {
                    Snackbar.make(v, "Name already exists. Please enter another.", Snackbar.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(Finish.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }
}
