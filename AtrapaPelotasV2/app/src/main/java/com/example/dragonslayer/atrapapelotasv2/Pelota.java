package com.example.dragonslayer.atrapapelotasv2;
import android.graphics.Bitmap;

import java.util.Random;
/**
 * Created by moises on 2/24/2015.
 */
public class Pelota extends Sprite{
    private long last;

    public Pelota(Bitmap bmap, float x, float y, float dx, float dy)
    {
        super(bmap, x, y, dx, dy);
        Random rnd = new Random((long) (x * y));
        long r = rnd.nextInt(3000);
        last = System.currentTimeMillis() + r;
    }
}
