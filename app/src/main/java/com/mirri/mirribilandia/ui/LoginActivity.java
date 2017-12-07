package com.mirri.mirribilandia.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
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
    }

    public void login() {
        Log.d(TAG, "Login");

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
                    Toast.makeText(getApplicationContext(), "Sono loggato", Toast.LENGTH_LONG).show();
                } else if(code == LOGIN_FAILED) {
                    Toast.makeText(getApplicationContext(), "Username e/o password errati", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Errore sconosciuto, riprovare", Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Errore durante il login", Toast.LENGTH_LONG).show();
            }

        } else {
            Log.d(TAG, "Errore durante il login");
            Toast.makeText(getApplicationContext(), "Errore durante il login", Toast.LENGTH_LONG).show();
        }
    }


}
