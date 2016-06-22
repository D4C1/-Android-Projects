package com.example.dragonslayer.atrapapelotasv2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {

    private AtrapaView view;
    private long lastUpdate = 0;
    private SensorManager sensorManager;
    private Sensor accelerometer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);

        PreferenceManager.setDefaultValues(this,R.xml.settings,false);

        view = (AtrapaView) findViewById(R.id.atrapaView);
        view.setTextView((TextView) findViewById(R.id.mssg));
        view.setState(AtrapaView.STATE_READY);

    }

    public void onSensorChanged(SensorEvent event)
    {
        Sensor sensor = event.sensor;

        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 20) {
                if (x > 1.0) {
                    view.mueveCanLeft();
                }
                if (x < -1.0) {
                    view.mueveCanRight();
                }

                lastUpdate = curTime;
            }
        }
    }

    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {
    }

    protected void onPause()
    {
        super.onPause();
        sensorManager.unregisterListener(this);
        view.pause();
    }

    protected void onResume()
    {
        super.onResume();
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean modo = settings.getBoolean("modo", false);
        view.usaSensor(modo);
        if (modo) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        else {
            sensorManager.unregisterListener(this);
        }
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
        switch (id) {

            case R.id.action_start:
                view.start();
                return true;

            case R.id.action_stop:
                view.setState(AtrapaView.STATE_LOSE, getText(R.string.mssg_stopped));
                return true;

            case R.id.action_pause:
                view.pause();
                return true;

            case R.id.action_resume:
                view.resume();
                return true;

            case R.id.action_settings:
                Intent intento = new Intent(this, SettingsActivity.class);
                startActivity(intento);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
