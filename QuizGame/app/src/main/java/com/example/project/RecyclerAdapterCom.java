package com.example.project;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

public class RecyclerAdapterCom extends RecyclerView.Adapter<RecyclerAdapterCom.ViewHolder> {
    //questions
    private final String[] titles = {
            "What is the primary function of RAM in a computer?",
            "Which programming language is commonly used for web development?",
            "What does HTML stand for?",
            "What is the main purpose of a firewall in network security?",
            "Which company developed the Java programming language?",
            "What does CPU stand for?",
            "What is the file extension for a Java source code file?",
            "What does LAN stand for?",
            "What is the process of converting source code into machine code called?",
            "Which programming language is known for its use in statistical computing and data analysis?",
            "What does GUI stand for?",
            "Which protocol is used for sending emails?",
            "What is the largest unit of storage in a computer?",
            "What is the purpose of a compiler?",
            "Which operating system is developed by Apple Inc.?"
    };
    //possible answers
    private final String[][] options = {
            {"Store data permanently", "Process data temporarily", "Control data flow"},
            {"Java", "Python", "JavaScript"},
            {"Hyper Text Markup Language", "High Tech Markup Language", "Home Tool Markup Language"},
            {"Monitor network traffic", "Block unauthorized access", "Enhance internet speed"},
            {"Microsoft", "Google", "Oracle"},
            {"Central Processing Unit", "Computer Programming Unit", "Control Processing Unit"},
            {".java", ".html", ".class"},
            {"Local Area Network", "Large Area Network", "Landline Access Node"},
            {"Compilation", "Interpretation", "Execution"},
            {"Python", "R", "C++"},
            {"Graphical User Interface", "General Utility Interface", "Global User Interaction"},
            {"SMTP", "HTTP", "FTP"},
            {"Bit", "Byte", "Gigabyte"},
            {"Translate source code to machine code", "Manage files and folders", "Monitor system performance"},
            {"macOS", "Windows", "Linux"}
    };
    //right answers
    private final String[] answers = {
            "Process data temporarily",
            "JavaScript",
            "Hyper Text Markup Language",
            "Block unauthorized access",
            "Oracle",
            "Central Processing Unit",
            ".java",
            "Local Area Network",
            "Compilation",
            "R",
            "Graphical User Interface",
            "SMTP",
            "Gigabyte",
            "Translate source code to machine code",
            "macOS"
    };

    private final String[] selectedOptions = new String[titles.length]; // Array to store selected options

    @NonNull
    @Override
    public RecyclerAdapterCom.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterCom.ViewHolder holder, int position) {
        holder.itemTitle.setText(titles[position]);

        String[] questionOptions = options[position];
        holder.optionAText.setText(questionOptions[0]);
        holder.optionBText.setText(questionOptions[1]);
        holder.optionCText.setText(questionOptions[2]);

        // Set selected option if available
        if (selectedOptions[position] != null) {
            String selectedOption = selectedOptions[position];
            if (selectedOption.equals(answers[position])) {
                holder.submitButton.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green));
                holder.submitButton.setText("RIGHT");
            } else {
                holder.submitButton.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.red));
                holder.submitButton.setText("WRONG");
            }
            // Disable radio buttons after submitting
            disableRadioButtons(holder);

            // Check the selected radio button
            if (selectedOption.equals(holder.optionAText.getText().toString())) {
                holder.optionARadioButton.setChecked(true);
            } else if (selectedOption.equals(holder.optionBText.getText().toString())) {
                holder.optionBRadioButton.setChecked(true);
            } else if (selectedOption.equals(holder.optionCText.getText().toString())) {
                holder.optionCRadioButton.setChecked(true);
            }
        } else {
            // Reset button state and enable radio buttons
            holder.submitButton.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), android.R.color.transparent));
            holder.submitButton.setText("SUBMIT");
            holder.radioGroup.clearCheck();
            holder.optionARadioButton.setEnabled(true);
            holder.optionBRadioButton.setEnabled(true);
            holder.optionCRadioButton.setEnabled(true);
        }

        holder.submitButton.setOnClickListener(v -> {
            int selectedOptionId = holder.radioGroup.getCheckedRadioButtonId();
            if (selectedOptionId != -1) {
                String selectedOptionText = getSelectedOptionText(selectedOptionId, holder);
                selectedOptions[position] = selectedOptionText; // Store selected option
                if (selectedOptionText.equals(answers[position])) {
                    holder.submitButton.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.green));
                    holder.submitButton.setText("RIGHT");
                } else {
                    holder.submitButton.setBackgroundColor(ContextCompat.getColor(holder.itemView.getContext(), R.color.red));
                    holder.submitButton.setText("WRONG");
                }
                disableRadioButtons(holder);
            } else {
                Snackbar.make(holder.itemView, "Please select an option", Snackbar.LENGTH_SHORT).show();
            }
        });
    }

    // Helper method to disable radio buttons
    private void disableRadioButtons(ViewHolder holder) {
        holder.optionARadioButton.setEnabled(false);
        holder.optionBRadioButton.setEnabled(false);
        holder.optionCRadioButton.setEnabled(false);
    }

    private String getSelectedOptionText(int selectedOptionId, ViewHolder holder) {
        if (selectedOptionId == R.id.option_a) {
            return holder.optionAText.getText().toString();
        } else if (selectedOptionId == R.id.option_b) {
            return holder.optionBText.getText().toString();
        } else if (selectedOptionId == R.id.option_c) {
            return holder.optionCText.getText().toString();
        } else {
            return ""; // Invalid selection
        }
    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemTitle, optionAText, optionBText, optionCText;
        RadioGroup radioGroup;
        RadioButton optionARadioButton, optionBRadioButton, optionCRadioButton;
        Button submitButton;

        public ViewHolder(View itemView) {
            super(itemView);

            itemTitle = itemView.findViewById(R.id.item_title);
            optionAText = itemView.findViewById(R.id.option_a);
            optionBText = itemView.findViewById(R.id.option_b);
            optionCText = itemView.findViewById(R.id.option_c);
            radioGroup = itemView.findViewById(R.id.radio_group);
            optionARadioButton = itemView.findViewById(R.id.option_a);
            optionBRadioButton = itemView.findViewById(R.id.option_b);
            optionCRadioButton = itemView.findViewById(R.id.option_c);
            submitButton = itemView.findViewById(R.id.btn);
        }
    }
    //method to get players score
    public int getScore() {
        int score = 0;
        for (int i = 0; i < titles.length; i++) {
            if (answers[i].equals(selectedOptions[i])) {
                score++;
            }
        }
        return score;
    }
}
