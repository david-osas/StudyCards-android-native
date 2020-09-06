package com.example.studycards;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studycards.database.Decks;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DecksRecyclerAdapter extends RecyclerView.Adapter<DecksRecyclerAdapter.DecksHolder> {
    private static List<Decks> decks;

    public static class DecksHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView deckTitle, cardNumber;
        CardView cardDeck;
        LinearLayout deckLayout;

        public DecksHolder(View view) {
            super(view);
            deckTitle = view.findViewById(R.id.deckItemTitle);
            cardNumber = view.findViewById(R.id.deckItemCardNumber);
            cardDeck = view.findViewById(R.id.deckCard);
            deckLayout = view.findViewById(R.id.deckLinearLayout);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Decks item = decks.get(getAdapterPosition());
            Intent intent = new Intent(context, DeckActivity.class);
            intent.putExtra("title",item.deckTitle);
            intent.putExtra("uid",item.uid);
            intent.putExtra("cardList", (Serializable) item.cardList);

            context.startActivity(intent);
        }
    }

    public DecksRecyclerAdapter(List<Decks> data){
        decks = data;
    }

    @NonNull
    @Override
    public DecksRecyclerAdapter.DecksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deck_list_item,parent,false);
        DecksHolder viewHolder = new DecksHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DecksRecyclerAdapter.DecksHolder holder, int position) {
        Decks item = decks.get(position);
        int size = item.cardList.size();
        String suffix = size == 1? size+" card": size+" cards";
        String title = item.deckTitle.length() > 10? item.deckTitle.substring(0,10) + "...": item.deckTitle;

        holder.deckTitle.setText(title);
        holder.cardNumber.setText(suffix);
    }

    @Override
    public int getItemCount() {
        return decks.size();
    }
}
