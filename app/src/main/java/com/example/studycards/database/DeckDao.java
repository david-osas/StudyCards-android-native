package com.example.studycards.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DeckDao {

    @Insert
    void insertDecks(Decks... decks);


    @Query("DELETE FROM decks WHERE uid= :id")
    void deleteDeck(int id);

    @Query("SELECT * FROM decks")
    List<Decks> getAllDecks();

    @Query("UPDATE decks SET card_list= :list WHERE uid= :id")
    void updateDecks(List<String[]> list, int id);
}
