package com.example.registrationtest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.registrationtest.Connection.ConnectionClass;
import com.example.registrationtest.Session.SaveSharedPreference;
import com.example.registrationtest.Session.SessionManager;

public class MainMenuActivity extends AppCompatActivity {

    TextView usernameText;
    Button bodyStatButton;
    Button trainingButton;
    Button logoutButton;

    SessionManager sessionManager;

    ConnectionClass connectionClass;
    private java.sql.Connection connection = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);


            connectionClass = new ConnectionClass();

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            usernameText = findViewById(R.id.mainMenuUsername);
            bodyStatButton = findViewById(R.id.bodyStatsButton);
            trainingButton= findViewById(R.id.trainingButton);
            logoutButton = findViewById(R.id.logoutButton);

            //inicjalizujemy sesje
            sessionManager = new SessionManager(getApplicationContext());

            //zdobywamy email z sesji
            String userEmail = sessionManager.getEmail();

            //ustawiamy text z textview jako email
            usernameText.setText(userEmail);

            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //inicjalizujemy alert dialog builder
                    AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                    //ustawiamy tytuł
                    builder.setTitle("Log out");
                    //ustawiamy wiadomosc
                    builder.setMessage("Are you sure to Log out?");
                    //set positive button
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //set Login false
                            sessionManager.setLogin(false);
                            //set Email Empty
                            sessionManager.setEmail("");

                            //po wylogowaniu resetujemy zapisane pasy
                            SaveSharedPreference.setEmail(getApplicationContext(),"");
                            SaveSharedPreference.setPassword(getApplicationContext(),"");

                            //wracamy to strony głównej
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            //konczymy aktywnosc
                            finish();
                        }
                    });

                    //ustawiamy przycisk nie
                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //dialog cancel
                            dialog.cancel();
                        }
                    });
                    //inicjalizujemy dialog alert
                    AlertDialog alertDialog = builder.create();
                    //pokaz alert
                    alertDialog.show();

                }
            });
        }



    }



//}