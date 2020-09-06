package com.example.studycards.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DeckDao {

    @Insert
    void insertDecks(Decks... decks);

    @Update
    void updateDecks(Decks... decks);

    @Delete
    void deleteDecks(Decks... decks);

    @Query("SELECT * FROM decks")
    List<Decks> getAllDecks();
}
