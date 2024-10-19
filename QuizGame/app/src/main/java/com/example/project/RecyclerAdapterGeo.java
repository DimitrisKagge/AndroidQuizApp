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

public class RecyclerAdapterGeo extends RecyclerView.Adapter<RecyclerAdapterGeo.ViewHolder> {
    //questions
    private final String[] titles = {
            "What is the capital of France?",
            "Which continent is home to the Amazon Rainforest?",
            "What is the longest river in the world?",
            "Which ocean is the largest?",
            "Which country is known as the Land of the Rising Sun?",
            "Which city is located at the mouth of the Amazon River?",
            "Which desert is the largest in the world?",
            "What is the tallest mountain on Earth?",
            "Which country is known as the 'Land Down Under'?",
            "Which continent is the smallest by land area?",
            "What is the largest island in the world?",
            "Which country is famous for its tulips and windmills?",
            "Which ocean is between North America and Europe?",
            "What is the capital of Italy?",
            "Which continent is Antarctica a part of?"
    };
    //possible answers
    private final String[][] options = {
            {"Paris", "London", "Rome"},
            {"Asia", "South America", "Africa"},
            {"Amazon", "Nile", "Mississippi"},
            {"Indian Ocean", "Atlantic Ocean", "Pacific Ocean"},
            {"China", "Japan", "India"},
            {"Buenos Aires", "Rio de Janeiro", "Manaus"},
            {"Sahara", "Gobi", "Antarctic"},
            {"Mount Kilimanjaro", "Mount Everest", "Mount Fuji"},
            {"Canada", "Australia", "Brazil"},
            {"Asia", "Europe", "Australia"},
            {"Greenland", "Australia", "Madagascar"},
            {"Germany", "Netherlands", "Belgium"},
            {"Pacific Ocean", "Arctic Ocean", "Atlantic Ocean"},
            {"Paris", "Berlin", "Rome"},
            {"Europe", "Asia", "Antarctica"}
    };
    //right answers
    private final String[] geographyAnswers = {
            "Paris",
            "South America",
            "Nile",
            "Pacific Ocean",
            "Japan",
            "Manaus",
            "Sahara",
            "Mount Everest",
            "Australia",
            "Australia",
            "Greenland",
            "Netherlands",
            "Atlantic Ocean",
            "Rome",
            "Antarctica"
    };

    private final String[] selectedOptions = new String[titles.length]; // Array to store selected options

    @NonNull
    @Override
    public RecyclerAdapterGeo.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new RecyclerAdapterGeo.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterGeo.ViewHolder holder, int position) {
        holder.itemTitle.setText(titles[position]);

        String[] questionOptions = options[position];
        holder.optionAText.setText(questionOptions[0]);
        holder.optionBText.setText(questionOptions[1]);
        holder.optionCText.setText(questionOptions[2]);

        // Set selected option if available
        if (selectedOptions[position] != null) {
            String selectedOption = selectedOptions[position];
            if (selectedOption.equals(geographyAnswers[position])) {
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
                if (selectedOptionText.equals(geographyAnswers[position])) {
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
    private void disableRadioButtons(RecyclerAdapterGeo.ViewHolder holder) {
        holder.optionARadioButton.setEnabled(false);
        holder.optionBRadioButton.setEnabled(false);
        holder.optionCRadioButton.setEnabled(false);
    }

    private String getSelectedOptionText(int selectedOptionId, RecyclerAdapterGeo.ViewHolder holder) {
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
            if (geographyAnswers[i].equals(selectedOptions[i])) {
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
