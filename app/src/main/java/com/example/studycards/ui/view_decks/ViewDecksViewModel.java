package com.example.studycards.ui.view_decks;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.studycards.database.AppDatabase;
import com.example.studycards.database.DeckDao;
import com.example.studycards.database.Decks;

import java.util.List;

public class ViewDecksViewModel extends AndroidViewModel {
    private MutableLiveData<List<Decks>> decks = new MutableLiveData<>();
    private DeckDao deckDao;

    public ViewDecksViewModel(@NonNull Application application) {
        super(application);

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                AppDatabase database = AppDatabase.getInstance(getApplication());
                deckDao = database.deckDao();
                decks.postValue(deckDao.getAllDecks());
            }
        });
    }

    public LiveData<List<Decks>> getDecks(){
        return decks;
    }

    public void deleteDeck(int position){
        List<Decks> values = decks.getValue();

        final int uid = values.get(position).uid;

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                deckDao.deleteDeck(uid);
            }
        });

        values.remove(position);
        decks.setValue(values);

    }

}
