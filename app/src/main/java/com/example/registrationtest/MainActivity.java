package com.example.registrationtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.example.registrationtest.Connection.ConnectionClass;
import com.example.registrationtest.Session.SaveSharedPreference;

public class MainActivity extends AppCompatActivity {

    //wynik testu polaczenia z baza sql
    private TextView textView;

    //przyciski przenoszace do logowania i rejestracji
    private Button loginButton;
    private Button registerButton;

    ConnectionClass connectionClass;
    private java.sql.Connection connection = null;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (SaveSharedPreference.getEmail(MainActivity.this).length() == 0){


            }else {
            startActivity(new Intent(getApplicationContext(),MainMenuActivity.class));
            //konczymy aktywnosc
            finish();
        }




        connectionClass = new ConnectionClass();

        textView = findViewById(R.id.textView);

        loginButton = findViewById(R.id.loginActivityButton);
        registerButton = findViewById(R.id.registerActivityButton);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            Class.forName(ConnectionClass.Classes);
            connection = DriverManager.getConnection(ConnectionClass.url, ConnectionClass.username, ConnectionClass.password);
            textView.setText("SUCCES");

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            textView.setText("FAILED TO CONNECT");
        }

    }

    public void sqlButton(View view){

        if(connection != null){
            Statement statement;
            try {
                statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("Select * FROM TEST_TABLE;");
                while (resultSet.next()){
                    textView.setText(resultSet.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }else {
            textView.setText("Connection is null ");
        }

    }

    public void changeToRegisterActivity(View view){
        startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
    }

    public void changeToBodyInfoActivity(View view){
        startActivity(new Intent(getApplicationContext(),BodyInfoActivity.class));
    }

    public void changeToLoginActivity(View view){
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
    }
}