package com.mirri.mirribilandia.ui;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mirri.mirribilandia.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.NoSuchPaddingException;

import static com.mirri.mirribilandia.util.Utilities.MY_PREFS_NAME;

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


        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
                loginButton.setEnabled(false);
            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
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
            Log.d(TAG, response.toString());
            try {
                final int code = response.getInt("code");

                if(code == LOGIN_SUCCESS) {

                    final Utente utente = new UtenteImpl(response.getJSONObject("extra").getJSONObject("utente"));
                    AccountManager.saveUser(utente, getApplicationContext());
                    //Passa in un'altra activity

                    SharedPreferences.Editor editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("username", username);
                    editor.putString("password", password);
                    editor.apply();

                    startActivity(new Intent(this, AttractionActivity.class));
                    finish();
                    //Toast.makeText(getApplicationContext(), "Sono loggato", Toast.LENGTH_LONG).show();
                } else if(code == LOGIN_FAILED) {
                    Toast.makeText(getApplicationContext(), "Username e/o password errati", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Errore sconosciuto, riprovare", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                loginButton.setEnabled(true);
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Errore durante il login", Toast.LENGTH_LONG).show();
            }

        } else {
            loginButton.setEnabled(true);
            Log.d(TAG, "Errore durante il login");
            Toast.makeText(getApplicationContext(), "Errore durante il login", Toast.LENGTH_LONG).show();
        }
    }


}
