package com.example.dragonslayer.atrapapelotasv2;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;
/**
 * Created by moises on 2/24/2015.
 */
public class Sprite {

    private Bitmap bmap;
    private float x, y, dx, dy;
    private RectF rect;

    public Sprite(Bitmap b, float u, float v, float du, float dv)
    {
        bmap = b;
        x = u;
        y = v;
        dx = du;
        dy = dv;
        rect = new RectF(x, y, x + bmap.getWidth(), y + bmap.getHeight());
    }

    public Sprite(Bitmap b, float u, float v)
    {
        this(b, u, v, 0, 0);
    }

    public void mueve(double dt)
    {
        x += dx * dt;
        y += dy * dt;
        rect.set(x, y, x + bmap.getWidth(), y + bmap.getHeight());
    }

    public void mueve(float u, float v, int w)
    {
        if (u > 0 && u < w - bmap.getWidth()) {
            x = u;
        }
        y = v;
        rect.set(x, y, x + bmap.getWidth(), y + bmap.getHeight());
    }

    public void pinta(Canvas canvas)
    {
        canvas.drawBitmap(bmap, x, y, null);
    }

    public float getX()
    {
        return x;
    }

    public float getY()
    {
        return y;
    }

    public int getHeight()
    {
        return bmap.getHeight();
    }

    public int getWidth()
    {
        return bmap.getWidth();
    }

    public RectF getRect()
    {
        return rect;
    }

    public boolean intersecta(Sprite s)
    {
        return getRect().intersect(s.getRect());
    }


}
