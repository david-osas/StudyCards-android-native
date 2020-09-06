package com.example.studycards;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.studycards.database.AppDatabase;
import com.example.studycards.database.DeckDao;
import com.example.studycards.database.Decks;

import java.util.ArrayList;
import java.util.List;

public class CreateDeckViewModel extends AndroidViewModel {
    private List<String[]> cards = new ArrayList<>();
    public String state;
    public String question = "", answer = "", title;
    private Decks newDeck = new Decks();

    public CreateDeckViewModel(@NonNull Application application) {
        super(application);
    }


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
            newDeck.deckTitle = title;
            newDeck.cardList = cards;
            ConnectDB connectDB = new ConnectDB();
            connectDB.execute();
            return true;
        }
    }

    private class ConnectDB extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase appDB = AppDatabase.getInstance(getApplication());
            DeckDao deckDao = appDB.deckDao();
            deckDao.insertDecks(newDeck);
            return null;
        }
    }

}



