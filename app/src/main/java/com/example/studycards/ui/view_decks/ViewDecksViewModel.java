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

import java.util.ArrayList;
import java.util.List;

public class ViewDecksViewModel extends AndroidViewModel {
    private MutableLiveData<List<Decks>> decks = new MutableLiveData<>();



    public ViewDecksViewModel(@NonNull Application application) {
        super(application);
        decks.setValue(new ArrayList<Decks>());
    }

    public LiveData<List<Decks>> getDecks(){
        if(decks.getValue().size() == 0){
            ConnectDB connectDB = new ConnectDB();
            connectDB.execute();
        }

        return decks;
    }

    private class ConnectDB extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            AppDatabase appDB = AppDatabase.getInstance(getApplication());
            DeckDao deckDao = appDB.deckDao();
            List<Decks> dbDecks = deckDao.getAllDecks();

            if(dbDecks.size() != 0){
                decks.postValue(dbDecks);
            }

            return null;
        }


    }
}
