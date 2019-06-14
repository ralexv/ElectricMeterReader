package com.example.electricmeterreader;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;
import org.jetbrains.annotations.NotNull;

import java.util.List;

interface ScanCallback{
    void OnScanResult(Element [] elements);
};

public class WiFiScanner {

    private BroadcastReceiver wifiScanReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {
            boolean success = intent.getBooleanExtra(
                    WifiManager.EXTRA_RESULTS_UPDATED, false);
            if (success) {
                scanSuccess();
            } else {
                // scan failure handling
                scanFailure();
            }
        }
    };
    private ScanCallback callback;
    private WifiManager wifiManager;

    public WiFiScanner(@NotNull Context context, ScanCallback callback_in) {
        callback = callback_in;
        wifiManager = (WifiManager)context.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION);
        context.registerReceiver(wifiScanReceiver, intentFilter);
    }

    public void startScan()
    {
        boolean success = wifiManager.startScan();
        if (!success) {
            // scan failure handling
            Log.d("Scan", "Start scan failed");
            scanFailure();
        }
    }

    private void scanSuccess() {
        Log.d("ScanResult", "Success");
        processScanResult(wifiManager.getScanResults());
    }

    private void scanFailure() {
        // handle failure: new scan did NOT succeed
        // consider using old scan results: these are the OLD results!
        Log.d("ScanResult", "Failure");
        processScanResult(wifiManager.getScanResults());
    }

    private void processScanResult(@NotNull List<ScanResult> wifiList)
    {
        Log.d("TAG", wifiList.toString());

        Element [] elements = new Element[wifiList.size()];
        int i = 0;
        for (ScanResult item: wifiList){
            elements[i] = new Element(item.SSID, item.capabilities, item.level);
            i++;
        }

        callback.OnScanResult(elements);
    }
}
