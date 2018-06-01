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
    CardClickListener cardClickListener;
    public CardAdapter(List<Card> cardList, CardClickListener listener) {
        this.cardList = cardList;
        cardClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        public TextView answerView;
        protected CardClickListener cardClickListener;

        public ViewHolder(View view, CardClickListener listener) {
            super(view);
            textView = view.findViewById(R.id.name_text_view);
            answerView = view.findViewById(R.id.answer_text_view);
            cardClickListener = listener;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            if (cardClickListener != null)
            {
                cardClickListener.onCardClicked(this);
            }
        }

    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_list_row, parent, false);
        return new ViewHolder(itemView,cardClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, int position) {
        String answerString = "";
        Card card = cardList.get(position);

        if (card.manaCost != null){
            answerString = answerString.concat(card.manaCost);
        }

        answerString = answerString.concat("\n" + card.type);

        if (card.text != null){
            answerString = answerString.concat("\n" + card.text);
        }

        if (card.power != null){
            answerString =  answerString.concat("\n" + card.power + "/" + card.toughness);
        }

        holder.textView.setText(card.name);
        holder.answerView.setText(answerString);
        holder.answerView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public interface CardClickListener
    {
        void onCardClicked(ViewHolder cardViewHolder);
    }

    public void setCardList(List<Card> cardList){
        this.cardList = cardList;
    }
}
