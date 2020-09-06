package com.example.studycards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.studycards.database.Decks;
import com.example.studycards.databinding.ActivityViewDecksBinding;

import java.util.ArrayList;
import java.util.List;

public class ViewDecksActivity extends AppCompatActivity {
    private ActivityViewDecksBinding binding;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private int columnsNumber = 2;
    private ViewDecksViewModel viewModel;
    private List<Decks> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewDecksBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        recyclerView = binding.decksRecyclerView;
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(ViewDecksActivity.this,columnsNumber);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DecksRecyclerAdapter(data);
        recyclerView.setAdapter(adapter);



        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewDecksViewModel.class);
        Observer<List<Decks>> deckObserver = new Observer<List<Decks>>() {
            @Override
            public void onChanged(List<Decks> decks) {
                data.clear();
                data.addAll(decks);
                adapter.notifyDataSetChanged();

                String text = getString(R.string.view_decks_title, Integer.toString(decks.size()));
                binding.viewDecksTitle.setText(text);
            }
        };
        viewModel.getDecks().observe(this,deckObserver);

    }
}