package com.dpex.segundata.Activity.ui.account;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dpex.segundata.Activity.ChangePassword;
import com.dpex.segundata.Activity.ChangePin;
import com.dpex.segundata.Activity.ResetActivity;
import com.dpex.segundata.R;

public class accountActivity extends AppCompatActivity {
    Button update, changePassword,changePin,resetPin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        update = findViewById(R.id.updateAccount);
        changePassword = findViewById(R.id.changePass);
        changePin = findViewById(R.id.changePin);
        resetPin = findViewById(R.id.resetPin);


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(accountActivity.this, updateAccountActivity.class);
            }
        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(accountActivity.this, ChangePassword.class);
                startActivity(intent);
            }
        });
        changePin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(accountActivity.this, ChangePin.class);
                startActivity(intent);
            }
        });
        resetPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(accountActivity.this, ResetActivity.class);
                startActivity(intent);
            }
        });
    }
}