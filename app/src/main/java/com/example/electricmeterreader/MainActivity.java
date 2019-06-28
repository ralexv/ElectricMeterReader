package com.example.electricmeterreader;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.view.*;
import android.widget.ListView;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceCategory;
import androidx.preference.PreferenceManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Vector;

public class MainActivity extends AppCompatActivity {

    private class MyScanCallback implements ScanCallback {
        private Activity context;

        public MyScanCallback(Activity context) {
            this.context = context;
        }

        private Vector<Element> FilterElements(Element[] elements)
        {
            Vector<Element> res = new Vector<>();
            SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
            String SSIDMAsk = sp.getString("SSIDMask", "");
            for (Element element : elements)
            {
                if (SSIDMAsk.contentEquals("") || element.getTitle().matches(SSIDMAsk)) {
                    res.add(element);
                }
            }
            return res;
        }

        @Override
        public void OnScanResult(Element[] elements) {

            Vector<Element> filteredElements = FilterElements(elements);

            Element [] readyElements = new Element[filteredElements.size()];
            filteredElements.toArray(readyElements);

            AdapterElements adapterElements = new AdapterElements(context, readyElements);
            ListView netList = (ListView) context.findViewById(R.id.listItem);
            netList.setAdapter(adapterElements);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final WiFiScanner scanner = new WiFiScanner(this, new MyScanCallback(this));

        FloatingActionButton fab = findViewById(R.id.fab);
        final Activity context = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(context,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION }, 1);
                scanner.startScan();
                Snackbar.make(view, R.string.scanning, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;

            case R.id.action_about: {
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
            }

            default:
                return super.onOptionsItemSelected(item);
        }
    }

 }

