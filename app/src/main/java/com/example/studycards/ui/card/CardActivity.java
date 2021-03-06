package com.example.studycards.ui.card;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;

import com.example.studycards.R;
import com.example.studycards.databinding.ActivityCardBinding;
import com.example.studycards.ui.BaseActivity;

public class CardActivity extends BaseActivity {
    private ActivityCardBinding binding;
    private CardViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCardBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        Intent intent = getIntent();

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(CardViewModel.class);

        viewModel.question = intent.getStringExtra("question");
        viewModel.answer = intent.getStringExtra("answer");

        String question = getString(R.string.question);
        String answer = getString(R.string.answer);

        viewModel.question = question +"\n"+ viewModel.question;
        viewModel.answer = answer +"\n"+ viewModel.answer;

        SpannableString spannableQuestion = new SpannableString(viewModel.question);
        SpannableString spannableAnswer = new SpannableString(viewModel.answer);
        spannableQuestion.setSpan(new UnderlineSpan(),0,question.length(),0);
        spannableAnswer.setSpan(new UnderlineSpan(),0,answer.length(),0);

        binding.cardQuestionText.setText(spannableQuestion);
        binding.cardAnswerText.setText(spannableAnswer);
    }

    public void toggleAnswer(View view){
        if(binding.answerButton.getText().toString().equals(getString(R.string.reveal_answer))){
            binding.cardAnswer.setVisibility(View.VISIBLE);
            binding.answerButton.setText(R.string.hide_answer);
        }else{
            binding.answerButton.setText(R.string.reveal_answer);
            binding.cardAnswer.setVisibility(View.INVISIBLE);
        }

    }
}