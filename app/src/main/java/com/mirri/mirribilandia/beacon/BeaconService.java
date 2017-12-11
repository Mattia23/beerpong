package com.mirri.mirribilandia.beacon;

import android.app.Service;
import android.content.*;
import android.os.*;
import android.widget.Toast;

import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.connection.scanner.ConfigurableDevicesScanner;
import com.mirri.mirribilandia.ui.AttractionActivity;

import java.util.List;

import static com.mirri.mirribilandia.util.Utilities.BEACON_ID;

public class BeaconService extends Service {

    public static final String EXTRA_SCAN_RESULT_ITEM_DEVICE = "com.estimote.configuration.SCAN_RESULT_ITEM_DEVICE";
    public static final Integer RSSI_THRESHOLD = -45;

    private ConfigurableDevicesScanner devicesScanner;

    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        devicesScanner = new ConfigurableDevicesScanner(this);

        Toast.makeText(this, "Service created!", Toast.LENGTH_SHORT).show();

        handler = new Handler();
        runnable = new Runnable() {
            public void run() {
                devicesScanner.scanForDevices(new ConfigurableDevicesScanner.ScannerCallback() {
                    @Override
                    public void onDevicesFound(List<ConfigurableDevicesScanner.ScanResultItem> list) {
                        if (!list.isEmpty()) {
                            ConfigurableDevicesScanner.ScanResultItem item = list.get(0);
                            if (item.rssi > RSSI_THRESHOLD) {
                                devicesScanner.stopScanning();
                                BEACON_ID = item.device.deviceId.toString();
                                Toast.makeText(context, item.device.deviceId.toString(), Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                });
                handler.postDelayed(runnable, 20000);
            }
        };

        handler.postDelayed(runnable, 3000);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(runnable);
        Toast.makeText(this, "Service stopped", Toast.LENGTH_LONG).show();
    }
}