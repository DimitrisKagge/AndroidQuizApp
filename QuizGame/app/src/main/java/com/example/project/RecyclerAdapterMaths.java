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

public class RecyclerAdapterMaths extends RecyclerView.Adapter<RecyclerAdapterMaths.ViewHolder> {
    //questions
    private final String[] titles = {
            "What is the value of π (pi)?",
            "How many sides does a triangle have?",
            "What is the result of 5 + 7?",
            "How many degrees are there in a right angle?",
            "What is the square root of 25?",
            "What is the value of 2 × 3?",
            "How many millimeters are there in a centimeter?",
            "What is the next number in the sequence: 2, 4, 6, ...?",
            "What is the area of a square with side length 4 units?",
            "How many sides does a pentagon have?",
            "What is the result of 8 ÷ 2?",
            "What is the value of 3² (3 raised to the power of 2)?",
            "How many centimeters are there in a meter?",
            "What is the perimeter of a rectangle with length 5 units and width 3 units?",
            "What is the value of 4 × 0?"
    };
    //possible answers
    private final String[][] options = {
            {"3.14", "2.71", "1.62"},
            {"3", "4", "5"},
            {"10", "11", "12"},
            {"45", "90", "180"},
            {"3", "5", "7"},
            {"4", "6", "8"},
            {"10", "100", "1000"},
            {"8", "10", "12"},
            {"8 square units", "12 square units", "16 square units"},
            {"4", "5", "6"},
            {"2", "4", "6"},
            {"6", "9", "12"},
            {"10", "100", "1000"},
            {"8 units", "12 units", "16 units"},
            {"0", "2", "4"}
    };
    //right answers
    private final String[] mathAnswers = {
            "3.14",
            "3",
            "12",
            "90",
            "5",
            "6",
            "10",
            "8",
            "16 square units",
            "5",
            "4",
            "9",
            "100",
            "16 units",
            "0"
    };

    private final String[] selectedOptions = new String[titles.length]; // Array to store selected options

    @NonNull
    @Override
    public RecyclerAdapterMaths.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new RecyclerAdapterMaths.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterMaths.ViewHolder holder, int position) {
        holder.itemTitle.setText(titles[position]);

        String[] questionOptions = options[position];
        holder.optionAText.setText(questionOptions[0]);
        holder.optionBText.setText(questionOptions[1]);
        holder.optionCText.setText(questionOptions[2]);

        // Set selected option if available
        if (selectedOptions[position] != null) {
            String selectedOption = selectedOptions[position];
            if (selectedOption.equals(mathAnswers[position])) {
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
                if (selectedOptionText.equals(mathAnswers[position])) {
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
    private void disableRadioButtons(RecyclerAdapterMaths.ViewHolder holder) {
        holder.optionARadioButton.setEnabled(false);
        holder.optionBRadioButton.setEnabled(false);
        holder.optionCRadioButton.setEnabled(false);
    }

    private String getSelectedOptionText(int selectedOptionId, RecyclerAdapterMaths.ViewHolder holder) {
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

    //method to get players score
    public int getScore() {
        int score = 0;
        for (int i = 0; i < titles.length; i++) {
            if (mathAnswers[i].equals(selectedOptions[i])) {
                score++;
            }
        }
        return score;
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
}
