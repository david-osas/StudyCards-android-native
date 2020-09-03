package com.example.studycards;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

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