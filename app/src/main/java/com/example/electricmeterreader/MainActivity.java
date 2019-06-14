package com.example.electricmeterreader;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;

import android.view.*;
import android.widget.ListView;
import androidx.core.app.ActivityCompat;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private class MyScanCallback implements ScanCallback {
        private Activity context;

        public MyScanCallback(Activity context) {
            this.context = context;
        }

        @Override
        public void OnScanResult(Element[] elements) {
            AdapterElements adapterElements = new AdapterElements(context, elements);
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
                Snackbar.make(view, "Scaning .... ", Snackbar.LENGTH_LONG)
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
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

 }

