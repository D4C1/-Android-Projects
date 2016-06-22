package com.example.dragonslayer.atrapapelotasv2;
import android.os.Bundle;
import android.preference.PreferenceActivity;
/**
 * Created by moises on 2/24/2015.
 */
public class SettingsActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle bundle)
    {
        super.onCreate(bundle);
        // Load preference data from XML
        addPreferencesFromResource(R.xml.settings);
    }
}
