package com.example.studycards.ui.deck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studycards.databinding.ActivityDeckBinding;
import com.example.studycards.ui.add_card.AddCardActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

public class DeckActivity extends AppCompatActivity {
    private ActivityDeckBinding binding;
    private DeckViewModel viewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeckBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        Intent intent = getIntent();

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(DeckViewModel.class);
        viewModel.title = intent.getStringExtra("title");
        viewModel.uid = intent.getIntExtra("uid",0);
        viewModel.cardList = (List<String[]>) intent.getSerializableExtra("cardList");

        binding.selectedDeck.setText(viewModel.title);
        recyclerView = binding.cardsRecyclerView;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        adapter = new CardsRecyclerAdapter(viewModel.cardList);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = binding.addCardFAB;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeckActivity.this, AddCardActivity.class);
                intent.putExtra("uid",viewModel.uid);
                intent.putExtra("title",viewModel.title);
                intent.putExtra("cardList", (Serializable) viewModel.cardList);
                startActivity(intent);
            }
        });
    }
}