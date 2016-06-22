package com.example.dragonslayer.memoriav2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

//    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
    public static final String HI_SCORES = "HiScores";
    public static String Dif;
    public boolean bolBorrarMenu = false, accion = false;
//    public static final String HI_SCORE1 = "HiScore1";
//    public static final String HI_SCORE2 = "HiScore2";
//    public static final String HI_SCORE3 = "HiScore3";
//    public static final String HI_SCORE4 = "HiScore4";
//    public static final String HI_SCORE5 = "HiScore5";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner spinner = (Spinner)findViewById(R.id.Dificultad_spinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this,R.array.Dificultad_array,R.layout.spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String ms = spinner.getSelectedItem().toString();
                SharedPreferences settings;
//                        SharedPreferences settings = getSharedPreferences(HI_SCORE1, MODE_PRIVATE);
                SharedPreferences.Editor editor;
                settings = getSharedPreferences(HI_SCORES, MODE_PRIVATE);
                editor = settings.edit();
                // continue with delete
//                        SharedPreferences.Editor editor = settings.edit();
                editor.putString("Dif", ms);
                editor.commit();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        SharedPreferences settings = getSharedPreferences(HI_SCORES, MODE_PRIVATE);
        try{
            Dif = settings.getString("Dif", "4 X 4");
        }catch (NumberFormatException e){
            Dif = "4 X 4";
        }
        if (!Dif.equals(null)) {
            int spinnerPostion = adapter.getPosition(Dif);
            spinner.setSelection(spinnerPostion);
        }

        if(bolBorrarMenu == true){
            bolBorrarMenu = false;
//            gotoborrar(this);
        }

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);

        savedInstanceState.putBoolean("BorarrMenu",accion);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        bolBorrarMenu = savedInstanceState.getBoolean("BorrarMenu");
    }


    public void gotoCreditos(View view){
        Intent intent = new Intent(this,ActividadCreditos.class);
        startActivity(intent);
    }

    public void gotoInstrucciones(View view){
        Intent intent = new Intent(this,ActividadInstrucciones.class);
        startActivity(intent);
    }

    public void gotoPuntuaciones(View view){
        Intent intent = new Intent(this,ActividadPuntuaciones.class);
        startActivity(intent);
    }

    public void gotoborrar(View view){
        accion = true;
        new AlertDialog.Builder(this)
                .setTitle("Borrar Puntuaciones?")
                .setMessage("Esta seguro de que desea borrar todas las puntuaciones?")
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences settings;
//                        SharedPreferences settings = getSharedPreferences(HI_SCORE1, MODE_PRIVATE);
                        SharedPreferences.Editor editor;
                        settings = getSharedPreferences(HI_SCORES, MODE_PRIVATE);
                        editor = settings.edit();
                        // continue with delete
//                        SharedPreferences.Editor editor = settings.edit();
                        editor.putInt("HI_SCORE1", 100);
                        editor.commit();
                        editor.putInt("HI_SCORE2",100);
                        editor.commit();
                        editor.putInt("HI_SCORE3",100);
                        editor.commit();

                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        accion = false;
    }

    public void IniciarJuego(View view){
        Intent intent = new Intent(this,Juego.class);
        startActivity(intent);
    }

}
