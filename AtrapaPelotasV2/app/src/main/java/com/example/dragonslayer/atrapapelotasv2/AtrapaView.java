package com.example.dragonslayer.atrapapelotasv2;

/**
 * Created by moises on 2/24/2015.
 */


import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class AtrapaView extends SurfaceView implements SurfaceHolder.Callback {

    public static final int STATE_LOSE    = 1;
    public static final int STATE_PAUSE   = 2;
    public static final int STATE_READY   = 3;
    public static final int STATE_RUNNING = 4;
    public static final int STATE_WIN     = 5;
    public long last = 0;
    private int aum = 0;

    private AtrapaThread thread;
    private Paint blackPaint, bblackPaint;
//    private ArrayList<Pelota> pelotas;
    private Canasta canasta;
    private ArrayList<Sprite> pelotas;
    private Bitmap canImage,PelotaImage;
    private double step = 1.0/30.0;
    private TextView statusText;
    private Handler handler;
    private SoundPool sound;
    private int canvasWidth = 1, canvasHeight = 1;
    private int failSound, okSound, dropSound;
    private int vidas,score;
    private boolean usaSensor = false;
    private int state;
    private SurfaceHolder surfaceHolder;

    public AtrapaView(Context context){
        super(context);
        inicia();
    }

    public AtrapaView(Context context,AttributeSet attrs){
        super(context,attrs);
        inicia();
    }
    public AtrapaView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        inicia();
    }

    private void inicia(){
        sound = new SoundPool(5,AudioManager.STREAM_MUSIC,0);
        failSound = sound.load(getContext(),R.raw.fail,1);
        okSound = sound.load(getContext(),R.raw.ok,1);
        dropSound = sound.load(getContext(),R.raw.drop,1);

        blackPaint = new Paint();
        blackPaint.setColor(Color.WHITE);
        blackPaint.setStyle(Paint.Style.FILL);

        bblackPaint = new Paint();
        bblackPaint.setColor(Color.BLACK);
        bblackPaint.setTextSize(70);

        canImage = BitmapFactory.decodeResource(getResources(),R.drawable.basket);
        PelotaImage = BitmapFactory.decodeResource(getResources(),R.drawable.redball);

        pelotas = new ArrayList<>();
        canasta = null;

        getHolder().addCallback(this);
        thread = new AtrapaThread(getHolder(),new Handler(){
            @Override
            public void handleMessage(Message m){
                int v = m.getData().getInt("viz");
                statusText.setVisibility(v == View.VISIBLE ? View.VISIBLE : View.INVISIBLE);
                statusText.setText(m.getData().getString("text"));
            }
        });
        setFocusable(true);
    }

    public void mueveCanLeft(){
        if (canasta != null){
            canasta.mueve(canasta.getX() - 100,canasta.getY(),this.getWidth());
        }
    }
    public void mueveCanRight()
    {
        if (canasta != null) {
            canasta.mueve(canasta.getX() + 100, canasta.getY(), this.getWidth());
        }
    }

    public void usaSensor(boolean b)
    {
        usaSensor = b;
    }

    public void start(){
        synchronized (surfaceHolder){
            vidas = 5;
            score = 0;
            aum = 0;
            pelotas.clear();
            canasta = new Canasta(canImage,(canvasWidth/2)- (canImage.getWidth() / 2), canvasHeight - canImage.getHeight());
            setState(STATE_RUNNING);
        }
    }

    public void pause()
    {
        Log.d("tag", "xyz en pause, state: " + state);
        if (state == STATE_RUNNING) {
            setState(STATE_PAUSE);
        }
    }

    public void resume()
    {
        setState(STATE_RUNNING);
    }

    public void setState(int s)
    {
        synchronized (surfaceHolder) {
            setState(s, null);
        }
    }

    public void setState(int s, CharSequence message)
    {
        synchronized (surfaceHolder) {
            state = s;
            if (state == STATE_RUNNING) {
                Message mssg = handler.obtainMessage();
                Bundle b = new Bundle();
                b.putString("text", "");
                b.putInt("viz", View.INVISIBLE);
                mssg.setData(b);
                handler.sendMessage(mssg);
            }
            else {
                Resources res = getContext().getResources();
                CharSequence str = "";
                if (state == STATE_READY) {
                    str = res.getText(R.string.state_ready);
                }
                else if (state == STATE_PAUSE) {
                    str = res.getText(R.string.state_pause);
                }
                else if (state == STATE_LOSE) {
                    str = res.getText(R.string.state_lose);
                }
                else if (state == STATE_WIN) {
                    str = res.getText(R.string.state_win);
                }
                if (message != null) {
                    str = message + "\n" + str;
                }

                Message mssg = handler.obtainMessage();
                Bundle b = new Bundle();
                b.putString("text", str.toString());
                b.putInt("viz", View.VISIBLE);
                mssg.setData(b);
                handler.sendMessage(mssg);
            }
        }
    }

    private void update(double dt){
        boolean cambio = false;
        int result = -1;

        synchronized (surfaceHolder){

            long now = System.currentTimeMillis();


            if ((now - last) > (4000 - aum)) {
                if (score%50 ==0){vidas++;}
                if (score == 100){aum += 500;}
//                if (score == 200){aum += 500;}
                if (aum<3000){aum += 100;}
                Random rand = new Random();
                int randomX = rand.nextInt(((this.getWidth()-70) - 0) + 1) + 0;
                int randomDY = rand.nextInt((8-3)+1)+3;
                last = now;
                pelotas.add(new Sprite(PelotaImage, randomX, 0, 0, this.getHeight() / randomDY));
                sound.play(dropSound,1,1,1,0,1);
            }

            for(Sprite p : pelotas){
                p.mueve(dt);
            }

            int i = 0;
            boolean borrado;
            int index = -1;
            for (i = 0;i<pelotas.size();i++){
                Sprite s = pelotas.get(i);
                if (s.intersecta(canasta)){
//                    vidas--;
                    score++;
                    index = i;
                    sound.play(okSound,1,1,1,0,1);
                    break;
                }

            }
            if (index >= 0){
                pelotas.remove(index);

            }
            i =0;
            while (i<pelotas.size()){
                if (pelotas.get(i).getY() >this.getHeight()){
                    vidas--;
                    sound.play(failSound,1,1,1,0,1);
                    pelotas.remove(i);

                }
                else {
                    i++;
                }
            }
            if (vidas <0){
                result =STATE_LOSE;
                cambio = true;


            }
            if (cambio){
                setState(result);
            }

        }
    }

    protected void pinta(Canvas canvas){
        if (canvas == null){
            return;
        }
        int w = this.getWidth();
        int h = this.getHeight();

        canvas.drawRect(0,0,w,h,blackPaint);
        canvas.drawText("Vidas: "+vidas+"  Puntuacion: "+score,10,60,bblackPaint);
        for (Sprite p:pelotas){
            p.pinta(canvas);

        }
        if (canasta != null){
            canasta.pinta(canvas);
        }
    }

    public boolean onTouchEvent(MotionEvent event){
        boolean handled = false;

        synchronized (surfaceHolder){
            if (state == STATE_READY || state == STATE_LOSE || state == STATE_WIN ) {
                start();
                handled = true;
            }
//            else if(){
//
//            }
            else if (state == STATE_PAUSE) {
                resume();
                handled = true;
            }
            else if (state == STATE_RUNNING) {
                if (usaSensor) {


                }
                else {
                    canasta.mueve(event.getX()-(canasta.getWidth()/2), this.getHeight() - canasta.getHeight(), this.getWidth());
                }

                handled = true;
            }
        }
        return handled;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
        canvasWidth = width;
        canvasHeight = height;
    }

    public void surfaceCreated(SurfaceHolder holder)
    {
        Log.d("tag", "xyz created state: " + state);
        if (state == STATE_PAUSE) {
            thread = new AtrapaThread(getHolder(), new Handler() {
                @Override
                public void handleMessage(Message m) {
                    int v = m.getData().getInt("viz");
                    statusText.setVisibility(v == View.VISIBLE ? View.VISIBLE : View.INVISIBLE);
                    statusText.setText(m.getData().getString("text"));
                }
            });
        }
        thread.setRunning(true);
        thread.start();
    }
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        if (thread == null) {
            return;
        }

        boolean retry = true;
        thread.setRunning(false);
        while (retry) {
            try {
                thread.join();
                retry = false;
            }
            catch (InterruptedException e) {
                Log.d("tag", "xyz error en SurfaceDestroyed");
            }
        }
    }

    public void setTextView(TextView textView)
    {
        statusText = textView;

    }

    private class AtrapaThread extends Thread{
        private boolean isRunning = false;

        public AtrapaThread(SurfaceHolder holder,Handler h){
            surfaceHolder = holder;
            handler = h;
        }

        public void setRunning(boolean run){
            isRunning = run;
        }
        public boolean isRunning(){
            return isRunning;
        }

        @Override
        public void run(){
            long last = System.currentTimeMillis(),now;
            double dt, gdt = 0;
            while(isRunning) {

                // frame
                now = System.currentTimeMillis();
                if (state == STATE_RUNNING) {
                    dt = Math.min(1, (now - last) / 1000.0);
                    gdt = gdt + dt;

                    while (gdt > step) {
                        gdt = gdt - step;
                        update(step);
                    }
                }
                last = now;

                Canvas c = null;
                try {
                    c = surfaceHolder.lockCanvas(null);
                    synchronized(surfaceHolder) {
                        AtrapaView.this.pinta(c);
                    }
                }
                finally {
                    if (c != null) {
                        surfaceHolder.unlockCanvasAndPost(c);
                    }
                }
            }
        }
    }
}
