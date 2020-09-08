package com.example.studycards.ui;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studycards.R;
import com.example.studycards.ui.create_deck.CreateDeckActivity;
import com.example.studycards.ui.view_decks.ViewDecksActivity;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void createDeck(View view){
        Intent intent = new Intent(MainActivity.this, CreateDeckActivity.class);
        startActivity(intent);
    }

    public void viewDecks(View view){
        Intent intent = new Intent(MainActivity.this, ViewDecksActivity.class);
        startActivity(intent);
    }

}