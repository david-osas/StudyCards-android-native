package com.example.studycards.ui.deck;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.studycards.R;
import com.example.studycards.database.Decks;
import com.example.studycards.databinding.ActivityDeckBinding;
import com.example.studycards.ui.BaseActivity;
import com.example.studycards.ui.add_card.AddCardActivity;
import com.example.studycards.ui.card.CardActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

public class DeckActivity extends BaseActivity {
    private ActivityDeckBinding binding;
    private DeckViewModel viewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private CardsRecyclerAdapter.OnItemClickListener listener;

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

        initializeListener();
        setUpRecyclerView();

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

    public void setUpRecyclerView(){
        recyclerView = binding.cardsRecyclerView;
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration itemDecor = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(itemDecor);
        adapter = new CardsRecyclerAdapter(viewModel.cardList, listener);
        recyclerView.setAdapter(adapter);
    }

    public void initializeListener(){
        listener = new CardsRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                String[] item = viewModel.cardList.get(position);
                Intent intent = new Intent(DeckActivity.this, CardActivity.class);
                intent.putExtra("question",item[0]);
                intent.putExtra("answer",item[1]);

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
        AlertDialog.Builder builder = new AlertDialog.Builder(DeckActivity.this);

        builder.setMessage(R.string.delete_dialog_message)
                .setTitle(R.string.delete_dialog_title)
                .setIcon(R.drawable.ic_trash)
                .setPositiveButton(R.string.delete_dialog_positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        viewModel.deleteCard(position);
                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton(R.string.delete_dialog_negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        return builder;
    }
}