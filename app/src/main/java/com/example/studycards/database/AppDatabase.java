package com.example.studycards.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Decks.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DeckDao deckDao();
    private static final String DB_NAME = "decks_db";
    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context){
        if(instance == null){
            AppDatabase db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME).build();
        }

        return instance;
    }
}
