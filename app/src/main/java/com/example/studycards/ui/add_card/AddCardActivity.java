package com.example.studycards.ui.add_card;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import com.example.studycards.R;
import com.example.studycards.databinding.ActivityAddCardBinding;
import com.example.studycards.ui.BaseActivity;
import com.example.studycards.ui.deck.DeckActivity;
import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.util.List;

public class AddCardActivity extends BaseActivity {
    private ActivityAddCardBinding binding;
    private AddCardViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddCardBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        Intent intent = getIntent();

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(AddCardViewModel.class);
        viewModel.state = "question";
        binding.addCardDetails.setText(viewModel.question);

        viewModel.uid = intent.getIntExtra("uid",0);
        viewModel.title = intent.getStringExtra("title");
        viewModel.cardList = (List<String[]>) intent.getSerializableExtra("cardList");
    }

    public void switchToQuestion(View view){
        viewModel.answer = binding.addCardDetails.getText().toString();
        binding.addCardQuestionButton.setBackgroundResource(R.color.colorAccent);
        binding.addCardAnswerButton.setBackgroundResource(R.color.button_dull);
        binding.addCardDetails.setText(viewModel.question);
        binding.addCardDetails.setHint(R.string.card_question);
        viewModel.state = "question";

    }

    public void switchToAnswer(View view){
        viewModel.question = binding.addCardDetails.getText().toString();
        binding.addCardAnswerButton.setBackgroundResource(R.color.colorAccent);
        binding.addCardQuestionButton.setBackgroundResource(R.color.button_dull);
        binding.addCardDetails.setText(viewModel.answer);
        binding.addCardDetails.setHint(R.string.card_answer);
        viewModel.state = "answer";
    }

    public void setter(){
        if(viewModel.state.equals("question")){
            viewModel.question = binding.addCardDetails.getText().toString();
        }else{
            viewModel.answer = binding.addCardDetails.getText().toString();
        }
    }

    public void submitCard(View view){
        setter();
        boolean isValid = viewModel.addCard();
        if(isValid){
            Snackbar.make(view, R.string.snackbar_add_card,Snackbar.LENGTH_SHORT).show();
            new CountDownTimer(1000,500){
                @Override
                public void onTick(long millisUntilFinished) {

                }
                @Override
                public void onFinish() {
                    Intent intent = new Intent(AddCardActivity.this, DeckActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("uid",viewModel.uid);
                    intent.putExtra("title",viewModel.title);
                    intent.putExtra("cardList", (Serializable) viewModel.cardList);
                    startActivity(intent);
                }
            }.start();
        }else{
            Snackbar.make(view, R.string.snackbar_create_card_error,Snackbar.LENGTH_SHORT).show();
        }
    }


}