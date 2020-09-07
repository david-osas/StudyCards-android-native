package com.example.studycards.ui.view_decks;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.studycards.R;
import com.example.studycards.database.Decks;
import com.example.studycards.databinding.ActivityViewDecksBinding;
import com.example.studycards.ui.deck.DeckActivity;

import java.io.Serializable;
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
    private DecksRecyclerAdapter.OnItemClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewDecksBinding.inflate(getLayoutInflater());
        View root = binding.getRoot();
        setContentView(root);

        viewModel = new ViewModelProvider.AndroidViewModelFactory(getApplication()).create(ViewDecksViewModel.class);

        initializeListener();
        setUpRecyclerView();

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

    public void setUpRecyclerView(){
        recyclerView = binding.decksRecyclerView;
        recyclerView.setHasFixedSize(true);
        layoutManager = new GridLayoutManager(ViewDecksActivity.this,columnsNumber);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DecksRecyclerAdapter(data, listener);
        recyclerView.setAdapter(adapter);
    }


    public void initializeListener(){
        listener = new DecksRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Decks item = data.get(position);
                Intent intent = new Intent(ViewDecksActivity.this, DeckActivity.class);
                intent.putExtra("title",item.deckTitle);
                intent.putExtra("uid",item.uid);
                intent.putExtra("cardList", (Serializable) item.cardList);

                startActivity(intent);
            }

            @Override
            public void onItemLongClick(int position) {
                AlertDialog alertDialog = buildDialog(position).create();
                alertDialog.show();
            }
        };
    }

    public AlertDialog.Builder buildDialog(final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(ViewDecksActivity.this);

        builder.setMessage(R.string.delete_dialog_message)
                .setTitle(R.string.delete_dialog_title)
                .setIcon(R.drawable.ic_trash)
                .setPositiveButton(R.string.delete_dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.deleteDeck(position);
                    }
                })
                .setNegativeButton(R.string.delete_dialog_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder;
    }
}