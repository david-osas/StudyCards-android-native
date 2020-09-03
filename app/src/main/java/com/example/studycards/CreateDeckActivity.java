package com.example.studycards;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.studycards.databinding.ActivityCreateDeckBinding;

public class CreateDeckActivity extends AppCompatActivity {

    private ActivityCreateDeckBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateDeckBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    public void switchQuestion(View view){
        binding.questionButton.setBackgroundResource(R.color.colorAccent);
        binding.answerButton.setBackgroundResource(R.color.button_dull);
        binding.details.setHint(R.string.card_question);
    }

    public void switchAnswer(View view){
        binding.answerButton.setBackgroundResource(R.color.colorAccent);
        binding.questionButton.setBackgroundResource(R.color.button_dull);
        binding.details.setHint(R.string.card_answer);
    }


}