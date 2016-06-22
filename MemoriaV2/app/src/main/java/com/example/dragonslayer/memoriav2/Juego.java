package com.example.dragonslayer.memoriav2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

/**
 * Created by moises on 5/13/2015.
 */

public class Juego extends Activity {

    public int x,y;
    public static final String HI_SCORES = "HiScores";
    public int total=0;
    private String dificultad;
    private static int ROW_COUNT = -1;
    private static int COL_COUNT = -1;
    private Context context;
    private Drawable backImage;
    private int [] [] cards;
    private List<Drawable> images;
    private Card firstCard;
    private Card seconedCard;
    private ButtonListener buttonListener;

    private static Object lock = new Object();

    int turns, hiScore;
    private TableLayout mainTable;
    private UpdateCardsHandler handler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        handler = new UpdateCardsHandler();
        loadImages();
        setContentView(R.layout.juego);

        backImage =  getResources().getDrawable(R.drawable.icon);

       /*
       ((Button)findViewById(R.id.ButtonNew)).setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			newGame();

		}


	});*/

        buttonListener = new ButtonListener();

        mainTable = (TableLayout)findViewById(R.id.TableLayout03);


        context  = mainTable.getContext();

        SharedPreferences settings;
        settings = getBaseContext().getSharedPreferences(HI_SCORES,Context.MODE_PRIVATE);

        try{
            dificultad = settings.getString("Dif",null);
        }catch (NumberFormatException e){
            dificultad = "4 X 4";
        }
//        Toast.makeText(this,dificultad,Toast.LENGTH_LONG).show();



        switch (dificultad){
            case "4 X 4":
                total =16;
                x = 4;
                y = 4;
                break;
            case "4 X 5":
                    total = 20;
                x = 4;
                y = 5;
                break;
            case "4 X 6":
                total = 24;
                x = 4;
                y = 6;
                break;
            default:
                return;
        }
        newGame(x,y);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putBoolean("cambio", true);
        savedInstanceState.putInt("turns", turns);
    }


    private void newGame(int c, int r) {
        ROW_COUNT = r;
        COL_COUNT = c;

        cards = new int [COL_COUNT] [ROW_COUNT];


        mainTable.removeView(findViewById(R.id.TableRow01));
        mainTable.removeView(findViewById(R.id.TableRow02));

        TableRow tr = ((TableRow)findViewById(R.id.TableRow03));
        tr.removeAllViews();

        mainTable = new TableLayout(context);
        tr.addView(mainTable);

        for (int y = 0; y < ROW_COUNT; y++) {
            mainTable.addView(createRow(y));
        }

        firstCard=null;
        loadCards();

        turns=0;
        ((TextView)findViewById(R.id.tv1)).setText("# de Intentos: "+turns);


    }

    private void loadImages() {
        images = new ArrayList<Drawable>();

        images.add(getResources().getDrawable(R.drawable.c1));
        images.add(getResources().getDrawable(R.drawable.c2));
        images.add(getResources().getDrawable(R.drawable.c3));
        images.add(getResources().getDrawable(R.drawable.c4));
        images.add(getResources().getDrawable(R.drawable.c5));
        images.add(getResources().getDrawable(R.drawable.c6));
        images.add(getResources().getDrawable(R.drawable.c7));
        images.add(getResources().getDrawable(R.drawable.c8));
        images.add(getResources().getDrawable(R.drawable.c9));
        images.add(getResources().getDrawable(R.drawable.c10));
        images.add(getResources().getDrawable(R.drawable.c11));
        images.add(getResources().getDrawable(R.drawable.c12));
        images.add(getResources().getDrawable(R.drawable.c13));
        images.add(getResources().getDrawable(R.drawable.c14));
        images.add(getResources().getDrawable(R.drawable.c15));
        images.add(getResources().getDrawable(R.drawable.c16));
        images.add(getResources().getDrawable(R.drawable.c17));
        images.add(getResources().getDrawable(R.drawable.c18));
        images.add(getResources().getDrawable(R.drawable.c19));
        images.add(getResources().getDrawable(R.drawable.c20));
        images.add(getResources().getDrawable(R.drawable.c21));

    }

    private void loadCards(){
        try{
            int size = ROW_COUNT*COL_COUNT;

            Log.i("loadCards()","size=" + size);

            ArrayList<Integer> list = new ArrayList<Integer>();

            for(int i=0;i<size;i++){
                list.add(new Integer(i));
            }


            Random r = new Random();

            for(int i=size-1;i>=0;i--){
                int t=0;

                if(i>0){
                    t = r.nextInt(i);
                }

                t=list.remove(t).intValue();
                cards[i%COL_COUNT][i/COL_COUNT]=t%(size/2);

                Log.i("loadCards()", "card["+(i%COL_COUNT)+
                        "]["+(i/COL_COUNT)+"]=" + cards[i%COL_COUNT][i/COL_COUNT]);
            }
        }
        catch (Exception e) {
            Log.e("loadCards()", e+"");
        }

    }

    private TableRow createRow(int y){
        TableRow row = new TableRow(context);
        row.setHorizontalGravity(Gravity.CENTER);

        for (int x = 0; x < COL_COUNT; x++) {
            row.addView(createImageButton(x,y));
        }
        return row;
    }

    private View createImageButton(int x, int y){
        Button button = new Button(context);
        button.setBackgroundDrawable(backImage);
        button.setId(100*x+y);
        button.setOnClickListener(buttonListener);
        return button;
    }

    class ButtonListener implements OnClickListener {


        @Override
        public void onClick(View v) {

            synchronized (lock) {
                if(firstCard!=null && seconedCard != null){
                    return;
                }
                int id = v.getId();
                int x = id/100;
                int y = id%100;
                turnCard((Button)v,x,y);
            }


        }

        private void turnCard(Button button,int x, int y) {
            button.setBackgroundDrawable(images.get(cards[x][y]));

            if(firstCard==null){
                firstCard = new Card(button,x,y);
            }
            else{

                if(firstCard.x == x && firstCard.y == y){
                    return; //the user pressed the same card
                }

                seconedCard = new Card(button,x,y);

                turns++;
                ((TextView)findViewById(R.id.tv1)).setText("Tries: "+turns);


                TimerTask tt = new TimerTask() {

                    @Override
                    public void run() {
                        try{
                            synchronized (lock) {
                                handler.sendEmptyMessage(0);
                            }
                        }
                        catch (Exception e) {
                            Log.e("E1", e.getMessage());
                        }
                    }
                };

                Timer t = new Timer(false);
                t.schedule(tt, 1300);
            }


        }

    }

    class UpdateCardsHandler extends Handler{

        @Override
        public void handleMessage(Message msg) {
            synchronized (lock) {
                checkCards();
            }
        }
        public void checkCards(){
            if(cards[seconedCard.x][seconedCard.y] == cards[firstCard.x][firstCard.y]){
                firstCard.button.setVisibility(View.INVISIBLE);
                seconedCard.button.setVisibility(View.INVISIBLE);
                total -=2;
            }
            else {
                seconedCard.button.setBackgroundDrawable(backImage);
                firstCard.button.setBackgroundDrawable(backImage);
            }

            firstCard=null;
            seconedCard=null;
            if (total == 0){
//                Toast.makeText(context, "Ganaste", Toast.LENGTH_LONG).show();
                AlertDialog.Builder b = new AlertDialog.Builder(Juego.this).setTitle("Ganaste!") .setMessage("Felicidades Ganaste!").setPositiveButton("Repetir", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                total = x*y;
                                newGame(x,y);
                            }
                        }).setNegativeButton("Menu", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                                // do nothing
                            }
                        }).setIcon(android.R.drawable.btn_star);

                SharedPreferences settings;
                settings = getBaseContext().getSharedPreferences(HI_SCORES,Context.MODE_PRIVATE);
                String punt ="";
                if(y == 4){
                    punt = "HI_SCORE1";
                    try{
                        hiScore = settings.getInt("HI_SCORE1",100);
                    }catch (NumberFormatException e){
                        hiScore = 100;
                    }
                }
                if (y == 5){
                    punt = "HI_SCORE2";
                    try{
                        hiScore = settings.getInt("HI_SCORE2",100);
                    }catch (NumberFormatException e){
                        hiScore = 100;
                    }
                }
                if (y == 6){
                    punt = "HI_SCORE3";
                    try{
                        hiScore = settings.getInt("HI_SCORE3",100);
                    }catch (NumberFormatException e){
                        hiScore = 100;
                    }
                }

                if(turns < hiScore){
                    b.setMessage("Felicidades Nueva Puntuacion Maxima.");
                    SharedPreferences.Editor editor;
                    editor =settings.edit();
                    editor.putInt(punt,turns);
                    editor.commit();

                }
                b.show();
            }
        }
    }

    public void cerrarJuego(View view){
        this.finish();
    }


}
