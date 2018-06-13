package com.kwok.boostercluester;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    List<Card> cardList;
    ClickListener mClickListener;
    public CardAdapter(List<Card> cardList, ClickListener listener) {
        this.cardList = cardList;
        mClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView textView;
        public TextView answerView;
        public TextView pictureIconView;
        public ImageView imageView;
        protected ClickListener mClickListener;

        public ViewHolder(View view, ClickListener listener) {
            super(view);
            textView = view.findViewById(R.id.name_text_view);
            answerView = view.findViewById(R.id.answer_text_view);
            pictureIconView = view.findViewById(R.id.picture_icon);
            imageView = view.findViewById(R.id.card_image);

            mClickListener = listener;
            textView.setOnClickListener(this);
            pictureIconView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v)
        {

            if (mClickListener == null)
            {
                return;
            }

            if (v.getId() == R.id.name_text_view)
            {
                mClickListener.onCardClicked(this);
            }

            if (v.getId() == R.id.picture_icon)
            {
                mClickListener.onImageIconClicked(this);
            }
        }

    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_list_row, parent, false);
        return new ViewHolder(itemView, mClickListener);
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

        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
        double height = (width * (88.0/63));
        int heightW = ((int) height);
        Picasso.get().load(card.imageUrl).resize(width, heightW).into(holder.imageView);

        holder.textView.setText(card.name);
        holder.answerView.setText(answerString);
        holder.answerView.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public interface ClickListener
    {
        void onCardClicked(ViewHolder cardViewHolder);

        void onImageIconClicked(ViewHolder cardViewHolder);
    }

    public void setCardList(List<Card> cardList){
        this.cardList = cardList;
    }
}
