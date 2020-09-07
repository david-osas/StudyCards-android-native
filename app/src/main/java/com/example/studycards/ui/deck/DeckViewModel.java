package com.example.studycards.ui.deck;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModel;

import com.example.studycards.database.AppDatabase;

import java.util.List;

public class DeckViewModel extends AndroidViewModel {
    public String title = "";
    public int uid;
    public List<String[]> cardList;

    public DeckViewModel(@NonNull Application application) {
        super(application);
    }

    public void deleteCard(final int position){
        cardList.remove(position);
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase appDatabase = AppDatabase.getInstance(getApplication());
                appDatabase.deckDao().updateDecks(cardList,uid);
            }
        });
    }

}
