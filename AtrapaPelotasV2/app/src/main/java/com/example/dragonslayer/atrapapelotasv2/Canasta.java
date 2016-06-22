package com.example.dragonslayer.atrapapelotasv2;
import android.graphics.Bitmap;
import android.widget.Spinner;

/**
 * Created by moises on 2/24/2015.
 */
public class Canasta extends Sprite{
    private long last;

    public Canasta (Bitmap bmap,float x,float y){
        super(bmap,x,y);
        last = System.currentTimeMillis();
    }
}
