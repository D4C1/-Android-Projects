package com.tesi.pintafunciones;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by TESI on 28/01/15.
 */
public class PintaFunciones extends View {
//    public static final float MIN_Y = -1.5f;
//    public static final float MAX_Y =  1.5f;
    Paint paint = new Paint();
    private float paso = 0.01f, desde, hasta,MIN_Y,MAX_Y;
    private ArrayList<PointF> coseno = null, seno = null, tang = null, cotang = null, sec = null, cosec = null;
    private Paint paintCoseno, paintSeno, paintTangente, paintCotangente, paintSecante, paintCosecante, paintEjes, paintNombres;

    public PintaFunciones(Context c, int what, float d, float h,float minimo, float maximo,float paso1)
    {
        super(c);

        desde = d;
        hasta = h;
        MAX_Y = minimo;
        MIN_Y = maximo;
        paso = paso1;

        if ((what & MainActivity.COSENO_MASK) == MainActivity.COSENO_MASK) {
            paintCoseno = new Paint();
            paintCoseno.setColor(Color.RED);
            coseno = new ArrayList<>();
            for (float x = desde; x < hasta; x += paso) {
                coseno.add(new PointF(x, (float) Math.cos(x)));
            }
        }

        if ((what & MainActivity.SENO_MASK) == MainActivity.SENO_MASK) {
            paintSeno = new Paint();
            paintSeno.setColor(Color.BLUE);
            seno = new ArrayList<>();
            for (float x = desde; x < hasta; x += paso) {
                seno.add(new PointF(x, (float) Math.sin(x)));
            }
        }
        if ((what & MainActivity.TANG_MASK) == MainActivity.TANG_MASK) {
            paintTangente = new Paint();
            paintTangente.setColor(Color.YELLOW);
            tang = new ArrayList<>();
            for (float x = desde; x < hasta; x += paso) {
                tang.add(new PointF(x, (float) Math.tan(x)));
            }
        }

        if ((what & MainActivity.COTTANG_MASK) == MainActivity.COTTANG_MASK) {
            paintCotangente = new Paint();
            paintCotangente.setColor(Color.CYAN);
            cotang = new ArrayList<>();
            for (float x = desde; x < hasta; x += paso) {
                double g = 1/Math.tan(x);
                cotang.add(new PointF(x, (float) g));
            }
        }

        if ((what & MainActivity.SEC_MASK) == MainActivity.SEC_MASK) {
            paintSecante = new Paint();
            paintSecante.setColor(Color.MAGENTA);
            sec = new ArrayList<>();
            for (float x = desde; x < hasta; x += paso) {
                double g = 1/Math.cos(x);
                sec.add(new PointF(x, (float) g));
            }
        }

        if ((what & MainActivity.COSEC_MASK) == MainActivity.COSEC_MASK) {
            paintCosecante = new Paint();
            paintCosecante.setColor(Color.GREEN);
            cosec = new ArrayList<>();
            for (float x = desde; x < hasta; x += paso) {
                double g = 1/Math.sin(x);
                cosec.add(new PointF(x, (float) g));
            }
        }

        paintEjes = new Paint();
        paintEjes.setColor(Color.WHITE);
//        paintEjes.setStyle(Paint.Style.STROKE);
//        paintEjes.setStrokeWidth(23);

        paintNombres = new Paint();
        paintNombres.setColor(Color.WHITE);
        paintNombres.setTextSize(30);
    }

    public void onDraw(Canvas canvas)
    {

        Transforma t = new Transforma();
        t.setParams(desde, MIN_Y, hasta, MAX_Y, 0, 0, getWidth(), getHeight());
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0,0,getWidth(),getHeight(),paint);

        // Pinta el coseno
        if (coseno != null) {
            for (PointF p: coseno) {
                PointF q = t.Window2Viewport(p.x, p.y);
                canvas.drawPoint(q.x, q.y, paintCoseno);
            }
        }

        // Pinta el seno
        if (seno != null) {
            for (PointF p: seno) {
                PointF q = t.Window2Viewport(p.x, p.y);
                canvas.drawPoint(q.x, q.y, paintSeno);
            }
        }

        if (tang != null) {
            for (PointF p: tang) {
                PointF q = t.Window2Viewport(p.x, p.y);
                canvas.drawPoint(q.x, q.y, paintTangente);
            }
        }

        if (cotang != null) {
            for (PointF p: cotang) {
                PointF q = t.Window2Viewport(p.x, p.y);
                canvas.drawPoint(q.x, q.y, paintCotangente);
            }
        }
        if (sec != null) {
            for (PointF p: sec) {
                PointF q = t.Window2Viewport(p.x, p.y);
                canvas.drawPoint(q.x, q.y, paintSecante);
            }
        }
        if (cosec != null) {
            for (PointF p: cosec) {
                PointF q = t.Window2Viewport(p.x, p.y);
                canvas.drawPoint(q.x, q.y, paintCosecante);
            }
        }

        // Pinta los ejes
        PointF q1 = t.Window2Viewport(0, MIN_Y);
        PointF q2 = t.Window2Viewport(0, MAX_Y);
        canvas.drawLine(q1.x, q1.y, q2.x, q2.y, paintEjes);

        canvas.drawText(Float.toString(MAX_Y), q1.x+1, q1.y, paintNombres);
        canvas.drawText(Float.toString(MIN_Y), q2.x+1, q2.y+30, paintNombres);

        q1 = t.Window2Viewport(desde, 0);
        q2 = t.Window2Viewport(hasta, 0);
        canvas.drawLine(q1.x, q1.y, q2.x, q2.y, paintEjes);

        canvas.drawText(Float.toString(desde), q1.x, q1.y - 10, paintNombres);
        canvas.drawText(Float.toString(hasta),q2.x-70,q2.y-10,paintNombres);



    }
}
