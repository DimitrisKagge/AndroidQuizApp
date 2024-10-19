package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class maths extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecyclerAdapterMaths adapter; // Use the specific adapter type

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_maths);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.recycler_view), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        //using recycler view
        recyclerView = findViewById(R.id.recycler_view);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerAdapterMaths(); // Initialize with questions
        recyclerView.setAdapter(adapter);

        //adding button functionality
        Button move = findViewById(R.id.Back);
        move.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(maths.this, MainActivity2.class);
                startActivity(intent2);
            }
        });
        //adding button functionality
        Button move2 = findViewById(R.id.Finish);
        move2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int score = adapter.getScore(); // Get the score from the adapter
                Intent intent = new Intent(maths.this, Finish.class);
                intent.putExtra("Category", "Maths");
                intent.putExtra("Score", score);
                startActivity(intent);
            }
        });
    }
}
