package com.example.studycards;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardsRecyclerAdapter extends RecyclerView.Adapter<CardsRecyclerAdapter.CardHolder> {
    private static List<String[]> cards;

    public static class CardHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textView;
        public CardHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.cardItem);

            textView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            String[] item = cards.get(getAdapterPosition());
            Intent intent = new Intent(context, CardActivity.class);
            intent.putExtra("question",item[0]);
            intent.putExtra("answer",item[1]);
            context.startActivity(intent);

        }
    }

    public CardsRecyclerAdapter(List<String[]> data){
        cards = data;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_list_item,parent,false);
        CardHolder cardHolder = new CardHolder(view);

        return cardHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
        String[] item = cards.get(position);
        String question = item[0].length() > 100? item[0].substring(0,100)+"..." : item[0];
        holder.textView.setText(question);
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }
}
