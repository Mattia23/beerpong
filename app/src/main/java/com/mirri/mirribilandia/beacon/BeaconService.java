package com.mirri.mirribilandia.beacon;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.*;
import android.os.*;
import android.widget.Toast;

import com.estimote.sdk.SystemRequirementsChecker;
import com.estimote.sdk.connection.scanner.ConfigurableDevicesScanner;
import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.ui.AttractionActivity;
import com.mirri.mirribilandia.ui.ChatActivity;
import com.mirri.mirribilandia.ui.RestaurantActivity;

import java.util.List;
import java.util.Objects;

import static com.mirri.mirribilandia.util.Utilities.BEACON_ID;

public class BeaconService extends Service {

    public static final String EXTRA_SCAN_RESULT_ITEM_DEVICE = "com.estimote.configuration.SCAN_RESULT_ITEM_DEVICE";
    public static final Integer RSSI_THRESHOLD = -45;

    private ConfigurableDevicesScanner devicesScanner;

    public Context context = this;
    public Handler handler = null;
    public static Runnable runnable = null;

    private Notification.Builder builder;
    private NotificationManager mNotificationManager;
    private NotificationChannel mChannel;
    private int notifyID = (int) System.currentTimeMillis();//new Random().nextInt(Integer.MAX_VALUE);

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {

        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(Objects.equals(intent.getAction(), "chat")) {
                    //TODO Nella chat activity devo controllare il beacon id in qualche modo
                    startActivity(new Intent(getApplicationContext(), ChatActivity.class));
                }else if(Objects.equals(intent.getAction(), "rest")){
                    startActivity(new Intent(getApplicationContext(), RestaurantActivity.class));
                }
            }
        };

        Intent iChat = new Intent("chat");
        PendingIntent piChat = PendingIntent.getBroadcast(getApplicationContext(), 0, iChat, PendingIntent.FLAG_UPDATE_CURRENT);
        IntentFilter ifChat = new IntentFilter("chat");
        getApplicationContext().registerReceiver(broadcastReceiver, ifChat);

        Intent iRest = new Intent("rest");
        PendingIntent piRest = PendingIntent.getBroadcast(getApplicationContext(), 0, iRest, PendingIntent.FLAG_UPDATE_CURRENT);
        IntentFilter ifRest = new IntentFilter("rest");
        getApplicationContext().registerReceiver(broadcastReceiver, ifRest);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String CHANNEL_ID = "mirribilandia_channel_id";// The id of the channel.
            CharSequence name = "mirribilandia";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            Notification.Action actionChat =
                    new Notification.Action.Builder(null, "Chat", piChat).build();
            Notification.Action actionRest =
                    new Notification.Action.Builder(null, "Ristoranti vicini", piRest).build();
            builder = new Notification.Builder(this, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setOnlyAlertOnce(true)
                    .addAction(actionChat)
                    .addAction(actionRest)
                    .setChannelId(CHANNEL_ID);
        }else {
            builder = new Notification.Builder(this)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setOnlyAlertOnce(true)
                    .addAction(0, "Chat", piChat)
                    .addAction(0, "Ristoranti vicini", piRest);
        }


        devicesScanner = new ConfigurableDevicesScanner(this);

        Toast.makeText(this, "Service created!", Toast.LENGTH_SHORT).show();

        //TODO quando si collega fai
        /*builder.setContentTitle("nome attrazione");
        builder.setContentText("tempo attesa");

        //e poi
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mNotificationManager.createNotificationChannel(mChannel);
            mNotificationManager.notify(notifyID, builder.build());
        } else {
            mNotificationManager.notify(notifyID, builder.build());
        }*/



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