package com.example.studycards.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;


@Entity
public class Decks {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name="deck_title")
    public String deckTitle;

    @ColumnInfo(name="card_list")
    public List<String[]> cardList;

}
