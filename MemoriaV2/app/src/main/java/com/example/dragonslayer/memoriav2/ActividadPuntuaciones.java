package com.example.dragonslayer.memoriav2;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;

/**
 * Created by moises on 5/12/2015.
 */
public class ActividadPuntuaciones extends Activity {

    private int hiScore;
    public static final String HI_SCORES = "HiScores";

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puntuaciones);

        TextView scoreView = (TextView)findViewById(R.id.HighScore1);
//        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        SharedPreferences settings;
        settings = getBaseContext().getSharedPreferences(HI_SCORES,Context.MODE_PRIVATE);

        try{
            hiScore = settings.getInt("HI_SCORE1",100);
        }catch (NumberFormatException e){
            hiScore = 100;
        }
        scoreView.setText(Integer.toString(hiScore));

        ////////////////////////////////////////////////////

        scoreView = (TextView)findViewById(R.id.HighScore2);
        try{
            hiScore = settings.getInt("HI_SCORE2",100);
        }catch (NumberFormatException e){
            hiScore = 100;
        }
        scoreView.setText(Integer.toString(hiScore));

        ////////////////////////////////////////////////////

        scoreView = (TextView)findViewById(R.id.HighScore3);
        try{
            hiScore = settings.getInt("HI_SCORE3",100);
        }catch (NumberFormatException e){
            hiScore = 100;
        }
        scoreView.setText(Integer.toString(hiScore));

        ///////////////////////////////////////////////////
    }

    public void cerrarPuntuaciones(View view){
        this.finish();
    }
}
