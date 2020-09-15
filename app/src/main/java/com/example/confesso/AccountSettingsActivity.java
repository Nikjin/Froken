package com.example.confesso;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AccountSettingsActivity extends AppCompatActivity {

    TextView textPass,textDelete;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        textPass=findViewById(R.id.textPass);
        textDelete=findViewById(R.id.textDelete);
        back=findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        textPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountSettingsActivity.this,ChangePassActivity.class));
            }
        });

        textDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountSettingsActivity.this,DeleteAccountActivity.class));
            }
        });



    }
}
