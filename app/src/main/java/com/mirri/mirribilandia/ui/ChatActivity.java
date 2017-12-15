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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        attraction_id = intent.getStringExtra("ATTRAZIONE_ID");
        buttonSend = findViewById(R.id.send);

        listView = findViewById(R.id.msgview);

        chatArrayAdapter = new ChatArrayAdapter(getApplicationContext(), R.layout.chat_right);
        listView.setAdapter(chatArrayAdapter);

        chatText = findViewById(R.id.msg);
        chatText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    return sendChatMessage();
                }
                return false;
            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                sendChatMessage();
            }
        });

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

        checkMsgReceivedBeforeOpenChat();
    }

    private void checkMsgReceivedBeforeOpenChat() {
        final Bundle data = new Bundle();
        Utente utente = AccountManager.getLoggedUser(getApplicationContext());
        data.putString("username", utente.getUsername());
        data.putString("attrazione", attraction_id);
        try {
            new UrlConnectionAsyncTask(new URL(getString(R.string.receive_msg_chat)), this, getApplicationContext()).execute(data);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private boolean sendChatMessage() {
        String msg = chatText.getText().toString();
        chatArrayAdapter.add(new ChatMessage(MY_SIDE, msg));
        /*
        * Invio al database
        * */
        final Bundle data = new Bundle();
        Utente utente = AccountManager.getLoggedUser(getApplicationContext());
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
                    for(int i = 0; i < messages.length(); i++) {
                        chatArrayAdapter.add(new ChatMessage(OTHER_SIDE, messages.getJSONObject(i).getString("messaggio")));
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