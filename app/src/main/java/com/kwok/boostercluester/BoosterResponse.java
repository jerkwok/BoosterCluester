package com.kwok.boostercluester;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BoosterResponse {

    @Expose
    @SerializedName("cards")
    public List<Card> temp;
}
