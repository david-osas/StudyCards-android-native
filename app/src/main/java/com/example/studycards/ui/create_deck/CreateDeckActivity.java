package com.example.studycards.ui.create_deck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.example.studycards.R;
import com.example.studycards.databinding.ActivityCreateDeckBinding;
import com.example.studycards.ui.BaseActivity;
import com.google.android.material.snackbar.Snackbar;

public class CreateDeckActivity extends BaseActivity {

    private ActivityCreateDeckBinding binding;
    private CreateDeckViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateDeckBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(CreateDeckViewModel.class);
        viewModel.state = "question";
        binding.details.setText(viewModel.question);

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
            Snackbar.make(view, R.string.snackbar_create_card,Snackbar.LENGTH_SHORT).show();
            binding.details.setText("");
        }else{
            Snackbar.make(view, R.string.snackbar_create_card_error,Snackbar.LENGTH_SHORT).show();
        }
    }

    public void submitDeck(View view){
        setter();
        viewModel.title = binding.deckTitle.getText().toString();
        boolean isValid = viewModel.addDeck();
        if(isValid){
            Snackbar.make(view, R.string.snackbar_add_deck,Snackbar.LENGTH_SHORT).show();
            new CountDownTimer(1000,500){
                @Override
                public void onTick(long millisUntilFinished) {

                }
                @Override
                public void onFinish() {
                    finish();
                }
            }.start();
        }else{
            Snackbar.make(view, R.string.snackbar_add_deck_error,Snackbar.LENGTH_SHORT).show();
        }

    }

}

