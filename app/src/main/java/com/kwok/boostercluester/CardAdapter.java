package com.kwok.boostercluester;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    List<Card> cardList;
    OnItemClickListener listener;
    public CardAdapter(List<Card> cardList, OnItemClickListener listener) {
        this.cardList = cardList;
        this.listener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        public TextView answerView;

        public ViewHolder(View view) {
            super(view);
            textView = view.findViewById(R.id.name_text_view);
            answerView = view.findViewById(R.id.answer_text_view);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_list_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, int position) {
        String answerString = "";
        Card card = cardList.get(position);

        answerString = answerString.concat(card.manaCost + "\n" +
                                            card.type + "\n" +
                                            card.text);

        holder.textView.setText(card.name);
        holder.answerView.setText(answerString);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
}
