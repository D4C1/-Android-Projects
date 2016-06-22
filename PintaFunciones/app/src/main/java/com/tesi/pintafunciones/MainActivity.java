package com.tesi.pintafunciones;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;


public class MainActivity extends Activity {
    public static final int SENO_MASK = 1;
    public static final int COSENO_MASK = 2;
    public static final int TANG_MASK = 4;
    public static final int COTTANG_MASK = 8;
    public static final int SEC_MASK = 16;
    public static final int COSEC_MASK = 32;

    public static final String FLAG_TAG = "flag";
    public static final String XINICIAL_TAG = "xinicial";
    public static final String XFINAL_TAG = "xfinal";
    public static final String YMINIMA_TAG = "yminima";
    public static final String YMAXIMA_TAG = "ymaxima";
    public static final String PASO_TAG = "paso";

    private int flag = 0;
    private float xinicial = 0, xfinal = 0 ,yminima = 0, ymaxima = 0,paso = 00.01f;


    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        setContentView(R.layout.activity_main);

        if (bundle != null) {
            flag = bundle.getInt(FLAG_TAG);
            xinicial = bundle.getFloat(XINICIAL_TAG);
            xfinal = bundle.getFloat(XFINAL_TAG);
            ymaxima = bundle.getFloat(YMAXIMA_TAG);
            yminima = bundle.getFloat(YMINIMA_TAG);

            PintaFunciones p = new PintaFunciones(this, flag, xinicial, xfinal, yminima, ymaxima, paso);
            setContentView(p);
        }
    }

    public void Pintar(View view)
    {
        flag = 0;

        RadioButton r = (RadioButton) findViewById(R.id.sine);
        if (r.isChecked()) {
            flag |= SENO_MASK;
        }

        r = (RadioButton) findViewById(R.id.cosine);
        if (r.isChecked()) {
            flag |= COSENO_MASK;
        }
        r = (RadioButton) findViewById(R.id.tang);
        if (r.isChecked()) {
            flag |= TANG_MASK;
        }
        r = (RadioButton) findViewById(R.id.cotang);
        if (r.isChecked()) {
            flag |= COTTANG_MASK;
        }
        r = (RadioButton) findViewById(R.id.sec);
        if (r.isChecked()) {
            flag |= SEC_MASK;
        }
        r = (RadioButton) findViewById(R.id.cosec);
        if (r.isChecked()) {
            flag |= COSEC_MASK;
        }


        EditText e = (EditText) findViewById(R.id.xinicial);
        xinicial = Float.parseFloat(e.getText().toString());

        e = (EditText) findViewById(R.id.xfinal);
        xfinal = Float.parseFloat(e.getText().toString());

        e = (EditText) findViewById(R.id.ymaxima);
        ymaxima = Float.parseFloat(e.getText().toString());

        e = (EditText) findViewById(R.id.yminima);
        yminima = Float.parseFloat(e.getText().toString());

        e = (EditText) findViewById(R.id.paso);
        paso = Float.parseFloat(e.getText().toString());

        r = (RadioButton) findViewById(R.id.grados);
        if (r.isChecked()){
            double x1 = xinicial, x2 = xfinal;

            x1 = Math.toRadians(x1);
            x2 = Math.toRadians(x2);
            xinicial = (float) x1;
            xfinal = (float) x2;
        }
        // Esconde el teclado
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        Log.d("tag", "xyz flag: " + flag);
        PintaFunciones p = new PintaFunciones(this, flag, xinicial, xfinal, yminima, ymaxima, paso);
        setContentView(p);
    }

    public void onBackPressed()
    {
        setContentView(R.layout.activity_main);

        //super.onBackPressed();
    }

    public void onSaveInstanceState(Bundle bundle)
    {
        bundle.putInt(FLAG_TAG, flag);
        bundle.putFloat(XINICIAL_TAG, xinicial);
        bundle.putFloat(XFINAL_TAG, xfinal);
        bundle.putFloat(YMINIMA_TAG,yminima);
        bundle.putFloat(YMAXIMA_TAG,ymaxima);
        bundle.putFloat(PASO_TAG,paso);
        super.onSaveInstanceState(bundle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
