package com.example.studycards.ui.view_decks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studycards.R;
import com.example.studycards.database.Decks;

import java.util.List;

public class DecksRecyclerAdapter extends RecyclerView.Adapter<DecksRecyclerAdapter.DecksHolder> {
    private static List<Decks> decks;
    private static OnItemClickListener listener;
    private Context context;

    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemLongClick(int position);
    }


    public static class DecksHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        TextView deckTitle, cardNumber;
        CardView cardDeck;
        LinearLayout deckLayout;

        public DecksHolder(View view) {
            super(view);
            deckTitle = view.findViewById(R.id.deckItemTitle);
            cardNumber = view.findViewById(R.id.deckItemCardNumber);
            cardDeck = view.findViewById(R.id.deckCard);
            deckLayout = view.findViewById(R.id.deckLinearLayout);

            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(getAdapterPosition());
        }


        @Override
        public boolean onLongClick(View v) {
            listener.onItemLongClick(getAdapterPosition());
            return true;
        }
    }

    public DecksRecyclerAdapter(List<Decks> data, OnItemClickListener listener){
        decks = data;
        this.listener = listener;
    }

    @NonNull
    @Override
    public DecksRecyclerAdapter.DecksHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.deck_list_item,parent,false);
        DecksHolder viewHolder = new DecksHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DecksRecyclerAdapter.DecksHolder holder, int position) {
        Decks item = decks.get(position);
        String suffix = context.getResources().getQuantityString(R.plurals.card_number,item.cardList.size(), item.cardList.size());
        String title = item.deckTitle.length() > 10? item.deckTitle.substring(0,10) + "...": item.deckTitle;

        holder.deckTitle.setText(title);
        holder.cardNumber.setText(suffix);
    }

    @Override
    public int getItemCount() {
        return decks.size();
    }
}
