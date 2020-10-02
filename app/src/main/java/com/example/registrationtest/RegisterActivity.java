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
import android.widget.TextView;

import com.example.registrationtest.Connection.ConnectionClass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class RegisterActivity extends AppCompatActivity {

    EditText username,email,password;
    Button registerbtn, mainMenuButton;
    TextView status;
    Connection con;
    Statement stmt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText)findViewById(R.id.name);
        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        registerbtn = (Button)findViewById(R.id.registerbtn);
        mainMenuButton = findViewById(R.id.mainMenyButton);
        status = (TextView)findViewById(R.id.status);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new RegisterActivity.registeruser().execute("");
            }
        });
    }

    public class registeruser extends AsyncTask<String, String , String> {

        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {
            status.setText("Sending Data to Database");
        }

        @Override
        protected void onPostExecute(String s) {
            status.setText("Registration Successful");
            username.setText("");
            email.setText("");
            password.setText("");

            //po pomyślnej rejestracji przenosi nas do formularza na temat naszego ciała
            //startActivity(new Intent(getApplicationContext(),BodyInfoActivity.class));
        }

        @Override
        protected String doInBackground(String... strings) {
            try{
                con = connectionClass();
                if(con == null){
                    z = "Check Your Internet Connection";
                }
                else{
                    String sql = "INSERT INTO USERS(username,email,password) VALUES ('"+username.getText()+"','"+email.getText()+"','"+password.getText()+"')";
                    stmt = con.createStatement();
                    stmt.executeUpdate(sql);
                }

            }catch (Exception e){
                isSuccess = false;
                z = e.getMessage();
            }

            return z;
        }
    }

    public void changeToMainMenuActivity(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
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
