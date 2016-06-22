package com.example.dragonslayer.gato2jugadores;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    int T = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public void Reset(View view){
        Button boton1 = (Button) findViewById(R.id.boton1);
        Button boton2 = (Button) findViewById(R.id.boton2);
        Button boton3 = (Button) findViewById(R.id.boton3);
        Button boton4 = (Button) findViewById(R.id.boton4);
        Button boton5 = (Button) findViewById(R.id.boton5);
        Button boton6 = (Button) findViewById(R.id.boton6);
        Button boton7 = (Button) findViewById(R.id.boton7);
        Button boton8 = (Button) findViewById(R.id.boton8);
        Button boton9 = (Button) findViewById(R.id.boton9);

        boton1.setText("");
        boton2.setText("");
        boton3.setText("");
        boton4.setText("");
        boton5.setText("");
        boton6.setText("");
        boton7.setText("");
        boton8.setText("");
        boton9.setText("");

        T=0;
    }

    public void Gato(View view){

        if(Gano() && T>0){
            return;
        }
        Button boton = (Button)view;
        if (boton.getText().toString() == ""){
            if (T%2 == 0){
                boton.setText("X");
            }
            else {
                boton.setText("O");
            }
            T += 1;
            Gano();
        }


    }

    public boolean Gano(){

        Button boton1 = (Button) findViewById(R.id.boton1);
        Button boton2 = (Button) findViewById(R.id.boton2);
        Button boton3 = (Button) findViewById(R.id.boton3);
        Button boton4 = (Button) findViewById(R.id.boton4);
        Button boton5 = (Button) findViewById(R.id.boton5);
        Button boton6 = (Button) findViewById(R.id.boton6);
        Button boton7 = (Button) findViewById(R.id.boton7);
        Button boton8 = (Button) findViewById(R.id.boton8);
        Button boton9 = (Button) findViewById(R.id.boton9);

        if (T == 9){
            Toast.makeText(this,"Empate!!",Toast.LENGTH_LONG).show();
            return true;
        }

        if (boton1.getText().toString() == boton2.getText().toString() && boton2.getText().toString() == boton3.getText().toString() && boton1.getText().toString() != ""){
            if (boton1.getText() == "X"){
                Toast.makeText(this,"Gano X!!",Toast.LENGTH_LONG).show();
                return true;
            }
            else {
                Toast.makeText(this,"Gano O!!",Toast.LENGTH_LONG).show();
                return true;
            }
        }

        if (boton4.getText().toString() == boton5.getText().toString() && boton5.getText().toString() == boton6.getText().toString() && boton4.getText().toString() != ""){
            if (boton4.getText() == "X"){
                Toast.makeText(this,"Gano X!!",Toast.LENGTH_LONG).show();
                return true;
            }
            else {
                Toast.makeText(this,"Gano O!!",Toast.LENGTH_LONG).show();
                return true;
            }
        }

        if (boton7.getText().toString() == boton8.getText().toString() && boton8.getText().toString() == boton9.getText().toString() && boton7.getText().toString() != ""){
            if (boton7.getText() == "X"){
                Toast.makeText(this,"Gano X!!",Toast.LENGTH_LONG).show();
                return true;
            }
            else {
                Toast.makeText(this,"Gano O!!",Toast.LENGTH_LONG).show();
                return true;
            }
        }

        if (boton1.getText().toString() == boton4.getText().toString() && boton4.getText().toString() == boton7.getText().toString() && boton1.getText().toString() != ""){
            if (boton1.getText() == "X"){
                Toast.makeText(this,"Gano X!!",Toast.LENGTH_LONG).show();
                return true;
            }
            else {
                Toast.makeText(this,"Gano O!!",Toast.LENGTH_LONG).show();
                return true;
            }
        }

        if (boton2.getText().toString() == boton5.getText().toString() && boton5.getText().toString() == boton8.getText().toString() && boton2.getText().toString() != ""){
            if (boton2.getText() == "X"){
                Toast.makeText(this,"Gano X!!",Toast.LENGTH_LONG).show();
                return true;
            }
            else {
                Toast.makeText(this,"Gano O!!",Toast.LENGTH_LONG).show();
                return true;
            }
        }

        if (boton3.getText().toString() == boton6.getText().toString() && boton6.getText().toString() == boton9.getText().toString() && boton3.getText().toString() != ""){
            if (boton3.getText() == "X"){
                Toast.makeText(this,"Gano X!!",Toast.LENGTH_LONG).show();
                return true;
            }
            else {
                Toast.makeText(this,"Gano O!!",Toast.LENGTH_LONG).show();
                return true;
            }
        }
        if (boton1.getText().toString() == boton5.getText().toString() && boton5.getText().toString() == boton9.getText().toString() && boton1.getText().toString() != ""){
            if (boton1.getText() == "X"){
                Toast.makeText(this,"Gano X!!",Toast.LENGTH_LONG).show();
                return true;
            }
            else {
                Toast.makeText(this,"Gano O!!",Toast.LENGTH_LONG).show();
                return true;
            }
        }

        if (boton3.getText().toString() == boton5.getText().toString() && boton5.getText().toString() == boton7.getText().toString() && boton3.getText().toString() != ""){
            if (boton3.getText() == "X"){
                Toast.makeText(this,"Gano X!!",Toast.LENGTH_LONG).show();
                return true;
            }
            else {
                Toast.makeText(this,"Gano O!!",Toast.LENGTH_LONG).show();
                return true;
            }
        }
        return false;
    }
}
