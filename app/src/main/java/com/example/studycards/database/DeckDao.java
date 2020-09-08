package com.example.studycards.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

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
