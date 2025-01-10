package com.example.sitemate;

import android.os.Bundle;
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
                Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                displayData("Your name" + getName() + "\n" + "Your Email: " + getEmail() + "\n" + "Your Password : " + getPassword());
            }
        });
    }

    private void displayData(String textToDisplay){
        displayData.setText(textToDisplay);
    }

    private String getName(){
        name = getName.getText().toString();
        return name;
    }
    private String getEmail(){
        email = getEmail.getText().toString();
        return email;
    }
    private String getPassword(){
        password = getPassword.getText().toString();
        return password;
    }
}