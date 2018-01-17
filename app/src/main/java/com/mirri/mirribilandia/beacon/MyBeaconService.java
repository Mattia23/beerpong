package com.mirri.mirribilandia.beacon;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.widget.Toast;

import com.estimote.sdk.connection.scanner.ConfigurableDevicesScanner;
import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.item.AttractionContent;
import com.mirri.mirribilandia.item.RestaurantContent;
import com.mirri.mirribilandia.ui.ChatActivity;
import com.mirri.mirribilandia.ui.RestaurantActivity;

import java.util.List;
import java.util.Objects;

import static com.mirri.mirribilandia.util.Utilities.BEACON_ID;
import static com.mirri.mirribilandia.util.Utilities.SEARCH_BEACON;


public class MyBeaconService {

    public static final Integer RSSI_THRESHOLD = -45;

    private ConfigurableDevicesScanner devicesScanner;

    public Handler handler = null;
    public static Runnable runnable = null;

    private Notification.Builder builder;
    private NotificationManager mNotificationManager;
    private NotificationChannel mChannel;
    private int notifyID = (int) System.currentTimeMillis();

    public MyBeaconService(Context context){
        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(Objects.equals(intent.getAction(), "chat")) {
                    context.startActivity(new Intent(context, ChatActivity.class));
                }else if(Objects.equals(intent.getAction(), "rest")){
                    context.startActivity(new Intent(context, RestaurantActivity.class));
                }
            }
        };

        Intent iChat = new Intent("chat");
        PendingIntent piChat = PendingIntent.getBroadcast(context, 0, iChat, PendingIntent.FLAG_UPDATE_CURRENT);
        IntentFilter ifChat = new IntentFilter("chat");
        context.registerReceiver(broadcastReceiver, ifChat);

        Intent iRest = new Intent("rest");
        PendingIntent piRest = PendingIntent.getBroadcast(context, 0, iRest, PendingIntent.FLAG_UPDATE_CURRENT);
        IntentFilter ifRest = new IntentFilter("rest");
        context.registerReceiver(broadcastReceiver, ifRest);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String CHANNEL_ID = "mirribilandia_channel_id";// The id of the channel.
            CharSequence name = "mirribilandia";// The user-visible name of the channel.
            int importance = NotificationManager.IMPORTANCE_HIGH;
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            Notification.Action actionChat =
                    new Notification.Action.Builder(null, "Chat", piChat).build();
            Notification.Action actionRest =
                    new Notification.Action.Builder(null, "Ristoranti vicini", piRest).build();
            builder = new Notification.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setOnlyAlertOnce(true)
                    .addAction(actionChat)
                    .addAction(actionRest)
                    .setChannelId(CHANNEL_ID);
        }else {
            builder = new Notification.Builder(context)
                    .setSmallIcon(R.drawable.ic_notification)
                    .setOnlyAlertOnce(true)
                    .addAction(0, "Chat", piChat)
                    .addAction(0, "Ristoranti vicini", piRest);
        }


        devicesScanner = new ConfigurableDevicesScanner(context);

        Toast.makeText(context, "Searching beacon...", Toast.LENGTH_SHORT).show();


        handler = new Handler();
        runnable = () -> {
            devicesScanner.scanForDevices(list -> {
                if (!list.isEmpty() && SEARCH_BEACON) {
                    ConfigurableDevicesScanner.ScanResultItem item = list.get(0);
                    if (item.rssi > RSSI_THRESHOLD) {
                        //devicesScanner.stopScanning();
                        if(!item.device.deviceId.toString().equals(BEACON_ID)){
                            BEACON_ID = item.device.deviceId.toString();
                            String beacon_id = item.device.deviceId.toString();
                            List<AttractionContent.AttractionItem> attractionItems = AttractionContent.ITEMS;
                            for(AttractionContent.AttractionItem attractionItem: attractionItems){
                                if(attractionItem.idBeacon.equals(beacon_id)){
                                    builder.setContentTitle("Attrazione vicina: " + attractionItem.name);
                                    builder.setContentText("Tempo di attesa: "+attractionItem.waitingTime+" min");
                                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                                        mNotificationManager.createNotificationChannel(mChannel);
                                        mNotificationManager.notify(notifyID, builder.build());
                                    } else {
                                        mNotificationManager.notify(notifyID, builder.build());
                                    }
                                    //Fare la query per riottenere i ritoranti attractionItem.id
                                    new RestaurantContent(context, attractionItem.id);
                                }
                            }
                            //Toast.makeText(context, item.device.deviceId.toString(), Toast.LENGTH_SHORT).show();
                            //System.out.println("DEVICE "+item.device.deviceId.toString());
                        }
                    }
                }else{
                    devicesScanner.stopScanning();
                }
            });
            //handler.postDelayed(runnable, 3000);
        };

        handler.postDelayed(runnable, 3000);
    }

}
