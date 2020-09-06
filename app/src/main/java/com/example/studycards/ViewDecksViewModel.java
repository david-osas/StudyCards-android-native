package com.example.studycards;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.studycards.database.AppDatabase;
import com.example.studycards.database.DeckDao;
import com.example.studycards.database.Decks;

public class ViewDecksViewModel extends AndroidViewModel {
    private MutableLiveData<Decks[]> decks = new MutableLiveData<>();



    public ViewDecksViewModel(@NonNull Application application) {
        super(application);
        decks.setValue(new Decks[0]);
    }

    public LiveData<Decks[]> getDecks(){
        if(decks.getValue().length == 0){
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
            Decks[] dbDecks = deckDao.getAllDecks();

            if(dbDecks.length != 0){
                decks.postValue(dbDecks);
            }

            return null;
        }


    }
}
