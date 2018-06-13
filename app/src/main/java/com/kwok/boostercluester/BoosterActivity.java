package com.kwok.boostercluester;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BoosterActivity extends AppCompatActivity
        implements View.OnClickListener,
        CardAdapter.ClickListener
{

    private RecyclerView recyclerView;
    private CardAdapter adapter;

    MtgService mtgService;
    Button button;
    String cardListString = "";
    String setString = "";
    List<Card> cardList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booster_layout);

        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        button = findViewById(R.id.button);
        button.setOnClickListener(this);

        mtgService = MtgService.retrofit.create(MtgService.class);

        adapter = new CardAdapter(cardList, this);
        recyclerView.setAdapter(adapter);

        Intent intent = getIntent();
        setString = intent.getStringExtra("SET");
        if (setString.isEmpty()){
            generateBooster("som");
        }else{
            generateBooster(setString.toUpperCase());
        }
    }

    private void generateBooster(String setId) {
        cardListString = "";
        Call<BoosterResponse> call = mtgService.generateBooster(setId);

        call.enqueue(new Callback<BoosterResponse>() {
            @Override
            public void onResponse(Call<BoosterResponse> call, Response<BoosterResponse> response) {
                BoosterResponse bodyList = response.body();

                if(bodyList != null)
                {
                    cardList = bodyList.temp;
                    adapter.setCardList(cardList);
                    adapter.notifyDataSetChanged();
                }else{
                    Toast toast = Toast.makeText(BoosterActivity.this, "Set Not Found", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }

            @Override
            public void onFailure(Call<BoosterResponse> call, Throwable t) {
                Log.d("Log", "log");
            }
        });
    }

    @Override
    public void onClick(View v) {
        generateBooster(setString);
    }

    @Override
    public void onCardClicked(CardAdapter.ViewHolder cardViewHolder)
    {
        if (cardViewHolder.answerView.getVisibility() == View.GONE) {
            cardViewHolder.answerView.setVisibility(View.VISIBLE);
        }else{
            cardViewHolder.answerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onImageIconClicked(CardAdapter.ViewHolder cardViewHolder)
    {
        if (cardViewHolder.imageView.getVisibility() == View.GONE)
        {
            cardViewHolder.imageView.setVisibility(View.VISIBLE);
        }else{
            cardViewHolder.imageView.setVisibility(View.GONE);
        }
    }
}
