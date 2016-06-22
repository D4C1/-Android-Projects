package com.example.dragonslayer.memoriav2;

import android.app.Activity;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by moises on 5/12/2015.
 */
public class ActividadCreditos extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creditos);

//        ImageView image = (ImageView) findViewById(R.id.Imagen1);
    }

    public void cerrarCreditos(View view){
        this.finish();
    }
}
