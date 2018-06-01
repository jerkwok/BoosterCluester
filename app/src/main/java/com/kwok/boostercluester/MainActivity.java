package com.kwok.boostercluester;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, CardAdapter.OnItemClickListener{

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    MtgService mtgService;
    Button button;
    String cardListString = "";
    List<Card> cardList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        button = findViewById(R.id.button);
        button.setOnClickListener(this);

        mtgService = MtgService.retrofit.create(MtgService.class);

        generateBooster("som");


    }

    private void generateBooster(String setId) {
        cardListString = "";
        Call<BoosterResponse> call = mtgService.generateBooster(setId);

        call.enqueue(new Callback<BoosterResponse>() {
            @Override
            public void onResponse(Call<BoosterResponse> call, Response<BoosterResponse> response) {
                BoosterResponse bodyList = response.body();

                cardList = bodyList.temp;
                adapter = new CardAdapter(cardList, new CardAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        View rowView = recyclerView.getChildAt(position);
                        View answerView = rowView.findViewById(R.id.answer_text_view);

                        if (answerView.getVisibility() == View.GONE) {
                            answerView.setVisibility(View.VISIBLE);
                        }else{
                            answerView.setVisibility(View.GONE);
                        }
                    }
                }


                );
                recyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<BoosterResponse> call, Throwable t) {
                Log.d("Log", "log");
            }
        });
    }

    @Override
    public void onClick(View v) {
        generateBooster("som");
    }

    @Override
    public void onItemClick(View view, int position) {
        View rowView = recyclerView.getChildAt(position);
        View answerView = rowView.findViewById(R.id.answer_text_view);

        if (answerView.getVisibility() == View.GONE) {
            answerView.setVisibility(View.VISIBLE);
        }else{
            answerView.setVisibility(View.GONE);
        }
    }

}