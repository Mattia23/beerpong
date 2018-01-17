package com.mirri.mirribilandia.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.item.PhotoContent;
import com.mirri.mirribilandia.ui.chat.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import static com.mirri.mirribilandia.util.Utilities.MY_PREFS_NAME;


public class ChatActivity extends Activity implements UrlConnectionAsyncTask.UrlConnectionListener {

    private static final String TAG = "ChatActivity";
    private static final boolean MY_SIDE = true;
    private static final boolean OTHER_SIDE = false;

    private String attraction_id;
    private ChatArrayAdapter chatArrayAdapter;
    private ListView listView;
    private EditText chatText;
    private Button buttonSend;
    private Utente utente;
    private int lastMessageId;
    private boolean activityShown;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        attraction_id = intent.getStringExtra("ATTRAZIONE_ID");
        buttonSend = findViewById(R.id.send);
        lastMessageId = 0;
        listView = findViewById(R.id.msgview);
        activityShown = true;

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.chat_right);
        listView.setAdapter(chatArrayAdapter);
        utente = AccountManager.getLoggedUser(getApplicationContext());
        chatText = findViewById(R.id.msg);
        chatText.setOnKeyListener((v, keyCode, event) -> {
            if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                return sendChatMessage();
            }
            return false;
        });
        buttonSend.setOnClickListener(arg0 -> sendChatMessage());

        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                listView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });

        checkMsgReceived();

        Thread check = new Thread(() -> {
            while(activityShown) {
                try {
                    Thread.sleep(3000);
                    checkMsgReceived();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        check.start();
    }

    @Override
    protected void onPause() {
        activityShown = false;
        super.onPause();
    }

    private void checkMsgReceived() {
        final Bundle data = new Bundle();
        data.putString("username", utente.getUsername());
        data.putString("attrazione", attraction_id);
        data.putString("last_message", String.valueOf(lastMessageId));
        try {
            new UrlConnectionAsyncTask(new URL(getString(R.string.receive_msg_chat)), this, getApplicationContext()).execute(data);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private boolean sendChatMessage() {
        String msg = chatText.getText().toString();
        msg = msg.replace("'", "''");
        //chatArrayAdapter.add(new ChatMessage(MY_SIDE, msg));
        /*
        * Invio al database
        * */
        final Bundle data = new Bundle();
        data.putString("username", utente.getUsername());
        data.putString("attrazione", attraction_id);
        data.putString("messaggio", msg);
        data.putString("orario", "");
        try {
            new UrlConnectionAsyncTask(new URL(getString(R.string.add_new_msg_chat)), this, getApplicationContext()).execute(data);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        chatText.setText("");
        //side = !side;
        return true;
    }

    @Override
    public void handleResponse(JSONObject response, Bundle extra) {
        if(response.length() != 0) {
            Log.d(TAG, response.toString());
            try {
                final int code = response.getInt("code");

                if(code == MSG_SENT) {
                    Toast.makeText(getApplicationContext(), response.getString("message"), Toast.LENGTH_SHORT).show();
                } else if (code == MSG_RECEIVE) {
                    final JSONArray messages = response.getJSONObject("extra").getJSONArray("data");
                    boolean side;
                    for(int i = 0; i < messages.length(); i++) {
                        side = messages.getJSONObject(i).getString("username").equals(utente.getUsername()) ? MY_SIDE : OTHER_SIDE;
                        JSONObject msg = messages.getJSONObject(i);
                        chatArrayAdapter.add(new ChatMessage(side,msg.getString("messaggio"), msg.getString("username"), msg.getString("orario")));
                        if(i == (messages.length()-1)) {
                            lastMessageId = Integer.parseInt(messages.getJSONObject(i).getString("id"));
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Errore", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Errore", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Errore", Toast.LENGTH_SHORT).show();
        }
    }
}