package com.example.dragonslayer.sum;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.lang.reflect.Array;


public class MainActivity extends ActionBarActivity {

    private String operaciones[] = {"Suma","Resta","Multiplicacion","Division"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Spinner spinner = (Spinner) findViewById(R.id.operacion);
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,android.R.id.text1,operaciones);
        spinner.setAdapter(adaptador);
        spinner.setSelection(0);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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


    public void Sumar(View view){
        double numX = 0, numY = 0;
        EditText x = (EditText) findViewById(R.id.x);
        EditText y = (EditText) findViewById(R.id.y);
        try {
            numX = Double.parseDouble(x.getText().toString());
            numY = Double.parseDouble(y.getText().toString());
        }
        catch (Exception e){
            Toast.makeText(this,"Error en los numeros",Toast.LENGTH_LONG).show();
        }
        EditText suma = (EditText) findViewById(R.id.suma);
       double resultado = 0;
       Spinner spinner = (Spinner) findViewById(R.id.operacion);
       switch ((int)spinner.getSelectedItemId()){
           case 0:
               resultado = numX + numY;
               break;
           case 1:
               resultado = numX - numY;
               break;
           case 2:
               resultado = numX * numY;
               break;
           case 3:
               resultado = numX/numY;
               break;
       }
        suma.setText(resultado + "");
    }
}
