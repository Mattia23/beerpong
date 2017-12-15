package com.mirri.mirribilandia.ui;

import android.Manifest;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mirri.mirribilandia.R;
import com.mirri.mirribilandia.item.AttractionContent;
import com.mirri.mirribilandia.item.EventContent;
import com.mirri.mirribilandia.item.HotelContent;
import com.mirri.mirribilandia.item.PhotoContent;
import com.mirri.mirribilandia.item.RestaurantContent;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import static com.mirri.mirribilandia.util.Utilities.MY_PREFS_NAME;
import static com.mirri.mirribilandia.util.Utilities.UPDATE_CONTENTS;
import static com.mirri.mirribilandia.util.Utilities.UPDATE_PHOTO_CONTENT;
import static com.mirri.mirribilandia.util.Utilities.hasPermissions;

public class LoginActivity extends AppCompatActivity implements UrlConnectionAsyncTask.UrlConnectionListener {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    private EditText usernameText;
    private EditText passwordText;
    private Button loginButton;
    private TextView signupLink;
    private String username;
    private String password;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameText = (EditText) findViewById(R.id.input_username);
        passwordText = (EditText) findViewById(R.id.input_password);
        loginButton = (Button) findViewById(R.id.btn_login);
        signupLink = (TextView) findViewById(R.id.link_signup);


        loginButton.setOnClickListener(v -> {
            login();
            loginButton.setEnabled(false);
        });

        signupLink.setOnClickListener(v -> {
            // Start the Signup activity
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivityForResult(intent, REQUEST_SIGNUP);
        });

        if(UPDATE_CONTENTS){
            new AttractionContent(getApplicationContext());
            new HotelContent(getApplicationContext());
            new RestaurantContent(getApplicationContext());
            new EventContent(getApplicationContext());
            UPDATE_CONTENTS = false;
        }

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        SharedPreferences prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
        String username = prefs.getString("username", null);
        String password = prefs.getString("password", null);
        if (username != null && password != null) {
            usernameText.setText(username);
            passwordText.setText(password);
        }
    }

    public void login() {
        Log.d(TAG, "login");

        username = usernameText.getText().toString();
        password = passwordText.getText().toString();

        checkLogin(username,password);
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    private void checkLogin(String username, String password) {
        final Bundle data = new Bundle();
        data.putString("username", username);
        data.putString("password", password);
        try {
            new UrlConnectionAsyncTask(new URL(getString(R.string.login_url)), this, getApplicationContext()).execute(data);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleResponse(JSONObject response, Bundle extra) {
        if(response.length() != 0) {
            try {
                final int code = response.getInt("code");
                if(code == LOGIN_SUCCESS) {
                    if (UPDATE_PHOTO_CONTENT) {
                        new PhotoContent(getApplicationContext(), username, password);
                        UPDATE_PHOTO_CONTENT = false;
                    }
                    final Utente utente = new UtenteImpl(response.getJSONObject("extra").getJSONObject("utente"));
                    AccountManager.saveUser(utente, getApplicationContext());
                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.apply();
                    startActivity(new Intent(this, AttractionActivity.class));
                    finish();
                } else if(code == LOGIN_FAILED) {
                    loginButton.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "Username e/o password errati", Toast.LENGTH_LONG).show();
                } else {
                    loginButton.setEnabled(true);
                    Toast.makeText(getApplicationContext(), "Errore sconosciuto, riprovare", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                loginButton.setEnabled(true);
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Errore durante il login", Toast.LENGTH_LONG).show();
            }

        } else {
            loginButton.setEnabled(true);
            Toast.makeText(getApplicationContext(), "JSON vuoto", Toast.LENGTH_LONG).show();
        }
    }


}
