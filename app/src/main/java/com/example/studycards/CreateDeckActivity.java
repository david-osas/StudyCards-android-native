package com.example.studycards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.example.studycards.databinding.ActivityCreateDeckBinding;
import com.google.android.material.snackbar.Snackbar;

public class CreateDeckActivity extends AppCompatActivity {

    private ActivityCreateDeckBinding binding;
    private CreateDeckViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateDeckBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        viewModel = new ViewModelProvider(this).get(CreateDeckViewModel.class);
        viewModel.state = "question";

    }

    public void switchToQuestion(View view){
        viewModel.answer = binding.details.getText().toString();
        binding.questionButton.setBackgroundResource(R.color.colorAccent);
        binding.answerButton.setBackgroundResource(R.color.button_dull);
        binding.details.setText(viewModel.question);
        binding.details.setHint(R.string.card_question);
        viewModel.state = "question";

    }

    public void switchToAnswer(View view){
        viewModel.question = binding.details.getText().toString();
        binding.answerButton.setBackgroundResource(R.color.colorAccent);
        binding.questionButton.setBackgroundResource(R.color.button_dull);
        binding.details.setText(viewModel.answer);
        binding.details.setHint(R.string.card_answer);
        viewModel.state = "answer";

    }

    public void setter(){
        if(viewModel.state.equals("question")){
            viewModel.question = binding.details.getText().toString();
        }else{
            viewModel.answer = binding.details.getText().toString();
        }
    }

    public void createCard(View view){
        setter();
        boolean isValid = viewModel.addCard();
        if(isValid){
            Snackbar.make(view, "Card has been created",Snackbar.LENGTH_SHORT).show();
            binding.details.setText("");
        }else{
            Snackbar.make(view, "Kindly enter both question and answer details to add card",Snackbar.LENGTH_SHORT).show();
        }
    }

    public void submitDeck(View view){
        setter();
        viewModel.title = binding.deckTitle.getText().toString();
        boolean isValid = viewModel.addDeck();
        if(isValid){
            Snackbar.make(view, "Deck has been submitted",Snackbar.LENGTH_SHORT).show();
        }else{
            Snackbar.make(view, "Create at least one card and set a deck title to submit current deck",Snackbar.LENGTH_SHORT).show();
        }
        new CountDownTimer(1500,500){

            @Override
            public void onTick(long millisUntilFinished) {

            }
            @Override
            public void onFinish() {
                Intent intent = new Intent(CreateDeckActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }.start();

    }

}