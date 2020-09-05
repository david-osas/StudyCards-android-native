package com.example.studycards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.studycards.database.Decks;
import com.example.studycards.databinding.ActivityViewDecksBinding;

public class ViewDecksActivity extends AppCompatActivity {
    private ActivityViewDecksBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private int columnsNumber = 2;
    private ViewDecksViewModel viewModel;
    private Decks[] data = new Decks[0];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewDecksBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        recyclerView = binding.decksRecyclerView;
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(getApplicationContext(),columnsNumber);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DecksRecyclerAdapter(data);
        recyclerView.setAdapter(adapter);;

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewDecksViewModel.class);
        Observer<Decks[]> deckObserver = new Observer<Decks[]>() {
            @Override
            public void onChanged(Decks[] decks) {
                if(decks.length != 0){
                    data = decks;
                    adapter.notifyDataSetChanged();
                }
                String text = getString(R.string.view_decks_title, Integer.toString(decks.length));
                binding.viewDecksTitle.setText(text);

            }
        };
        viewModel.getDecks().observe(this,deckObserver);

    }
}