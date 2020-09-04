package com.example.studycards;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class CreateDeckViewModel extends ViewModel {
    private ArrayList<String[]> cards = new ArrayList<>();
    public String state;
    public String question = "", answer = "", title;


    public boolean addCard(){
        if(question.isEmpty() || answer.isEmpty()){
            return false;
        }else{
            String[] values = {question, answer};
            cards.add( values );
            question = "";
            answer = "";
            return true;
        }
    }

    public boolean addDeck(){
        String[] values = {question, answer};

        if(!question.isEmpty() && !answer.isEmpty()){
            cards.add( values );
        }
        if(cards.isEmpty() || title.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

}
