package com.example.sitemate;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    Button createAccount;
    TextView displayData;
    EditText getName, getEmail, getPassword, getConfirmedPassword;
    String name, email, password, confirmedPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        createAccount = findViewById(R.id.createAccount);
        displayData = findViewById(R.id.displayData);
        createAccount = findViewById(R.id.createAccount);
        getName = findViewById(R.id.enterName);
        getEmail = findViewById(R.id.enterEmail);
        getPassword = findViewById(R.id.enterPassword);
        getConfirmedPassword = findViewById(R.id.confirmPassword);

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmPassword();
                displayData("Your name : " + getName() + "\n" + "Your Email: " + getEmail() + "\n" + "Your Password : " + getPassword());
            }
        });
    }

    private void displayData(String textToDisplay){
        displayData.setText(textToDisplay);
    }

    private String getName(){
        boolean isNameValid = true;
        if(getName.getText().toString().isEmpty()){
            showToastOnMainThread("Enter Your Name");
            isNameValid = false;
        }
        else{
            name = getName.getText().toString();
        }
        return name;
    }
    private String getEmail(){
        boolean isEmailValid = true;
        if(getEmail.getText().toString().isEmpty()){
            showToastOnMainThread("Enter Your Email");
            isEmailValid = false;}
        else
        {email = getEmail.getText().toString();}
        return email;
    }

    private String getPassword(){
        boolean isPasswordValid = true;
        if(getPassword.getText().toString().isEmpty()){
            showToastOnMainThread("Enter Your Password");
            isPasswordValid = false;
        }
        else{
            password = getPassword.getText().toString();
        }
        return password;
    }

    private void confirmPassword(){
        boolean isPasswordSame = true;
        if(!getConfirmedPassword.getText().toString().equals(getPassword.getText().toString())){
            showToastOnMainThread("Passwords do not match");
            isPasswordSame = false;
        }
        else{
            confirmedPassword = getConfirmedPassword.getText().toString();
            Log.i("MainActivity", "Password is same");
        }
    }

    private boolean entryValidity(){
        boolean isValid = true;



        if(getEmail.getText().toString().isEmpty()){
            showToastOnMainThread("Enter Your Email");
            isValid = false;
        }

        if(getPassword.getText().toString().isEmpty()){
            showToastOnMainThread("Enter Your Password");
            isValid = false;
        }

        if(!getConfirmedPassword.getText().toString().equals(getPassword.getText().toString())){
            showToastOnMainThread("Passwords do not match");
            isValid = false;
        }
        return isValid;
    }

    private void showToastOnMainThread(final String message) {
        runOnUiThread(() -> Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show());
    }
}