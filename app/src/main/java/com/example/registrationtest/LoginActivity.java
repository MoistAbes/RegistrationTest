package com.example.registrationtest;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.registrationtest.Connection.ConnectionClass;
import com.example.registrationtest.Session.SaveSharedPreference;
import com.example.registrationtest.Session.SessionManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {

    //deklarujemy zmienne
    EditText emaillogin,passwordlogin;
    Button loginbtn,regbtn;

    Connection con;

    SessionManager sessionManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emaillogin = (EditText)findViewById(R.id.emaillogin);
        passwordlogin = (EditText)findViewById(R.id.passwordlogin);
        loginbtn = (Button)findViewById(R.id.loginbtn);
        regbtn = (Button)findViewById(R.id.regbtn);

        //inicjalizacja SessionManager
        sessionManager = new SessionManager(getApplicationContext());




        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Tekst z pola email
                String emailText = emaillogin.getText().toString().trim();
                String passwordText = passwordlogin.getText().toString().trim();

                //gdy Email jest pusty
                if(emailText.equals("")){
                    emaillogin.setError("Please enter Email");
                }

                //Gdy hasło jest puste
                if(passwordText.equals("")){
                    //pokaz wiadomosc z bledem
                    passwordlogin.setError("Please enter password");
                }


                //logowanie przy użyciu bazy danych
                new LoginActivity.checkLogin().execute("");

            }
        });

        regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public class checkLogin extends AsyncTask<String, String, String> {

        String z = null;
        Boolean isSuccess = false;

        String emailText = emaillogin.getText().toString().trim();
        String passwordText = passwordlogin.getText().toString().trim();


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(String s) {

        }

        @Override
        protected String doInBackground(String... strings) {
            con = connectionClass();
            if(con == null){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(LoginActivity.this,"Check Internet Connection", Toast.LENGTH_LONG).show();
                    }
                });
                z = "On Internet Connection";
            }
            else {
                try {
                    @SuppressLint("WrongThread") String sql = "SELECT * FROM USERS WHERE email = '" + emaillogin.getText() + "' AND password = '" + passwordlogin.getText() + "' ";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(sql);

                    if (rs.next()) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_LONG).show();
                            }
                        });
                        z = "Success";

                        //przechowaj login w sesji
                        sessionManager.setLogin(true);

                        //zachowaj email w sesji
                        sessionManager.setEmail(emailText);

                        //ustawiamy zapis do logowania po wyłączeniu aplikacji
                        SaveSharedPreference.setEmail(getApplicationContext(),emailText);
                        SaveSharedPreference.setPassword(getApplicationContext(),passwordText);

                        Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(LoginActivity.this, "Check email or password", Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                } catch (Exception e) {
                    isSuccess = false;
                    Log.e("SQL Error : ", e.getMessage());
                }
            }
            return z;
        }
    }

    @SuppressLint("NewApi")
    public Connection connectionClass(){
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        try{
            Class.forName(ConnectionClass.Classes);
            connection = DriverManager.getConnection(ConnectionClass.url, ConnectionClass.username, ConnectionClass.password);
        }catch (Exception e){
            Log.e("SQL Connection Error : ", e.getMessage());
        }

        return connection;
    }
}