package com.example.dragonslayer.memoriav2;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by moises on 5/12/2015.
 */
public class ActividadInstrucciones extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.instrucciones);

    }

    public void cerrarInstrucciones(View view){
        this.finish();
    }

}
