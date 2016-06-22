package com.example.dragonslayer.memoriav2;

import android.widget.Button;

/**
 * Created by moises on 5/13/2015.
 */
public class Card{

    public int x;
    public int y;
    public Button button;

    public Card(Button button, int x,int y) {
        this.x = x;
        this.y=y;
        this.button=button;
    }


}
