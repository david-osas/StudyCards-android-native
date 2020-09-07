package com.example.studycards.ui.add_card;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.room.ColumnInfo;

import com.example.studycards.database.AppDatabase;
import com.example.studycards.database.DeckDao;
import com.example.studycards.database.Decks;

import java.util.List;

public class AddCardViewModel extends AndroidViewModel {
    public String question = "", answer = "", title;
    public String state;
    public int uid;
    public List<String[]> cardList;

    public AddCardViewModel(@NonNull Application application) {
        super(application);
    }

    public boolean addCard(){
        if(question.isEmpty() || answer.isEmpty()){
            return false;
        }else{
            String[] values = {question, answer};
            cardList.add(values);
            ConnectDB connectDB = new ConnectDB();
            connectDB.execute();
            return true;
        }
    }

    private class ConnectDB extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase appDB = AppDatabase.getInstance(getApplication());
            DeckDao deckDao = appDB.deckDao();
            deckDao.updateDecks(cardList,uid);
            return null;
        }
    }
}
