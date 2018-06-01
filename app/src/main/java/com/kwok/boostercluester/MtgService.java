package com.kwok.boostercluester;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MtgService {

        @GET("sets/{id}/booster")
        Call<BoosterResponse> generateBooster(
                @Path("id") String id);

        public static final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.magicthegathering.io/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
}
