package com.example.confesso;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {


    EditText editEmail;
    TextView textInvalid,textSignup;
    Button butReset;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        editEmail=findViewById(R.id.editEmail);
        textInvalid=findViewById(R.id.textInvalid);
        textSignup=findViewById(R.id.textSignup);
        butReset=findViewById(R.id.butReset);


        auth = FirebaseAuth.getInstance();

        textSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ResetPasswordActivity.this,SignupActivity.class));

            }
        });

        butReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userEmail = editEmail.getText().toString();

                if (TextUtils.isEmpty(userEmail))
                {
                    textInvalid.setVisibility(View.VISIBLE);
                }

                else
                {
                    auth.sendPasswordResetEmail(userEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if (task.isSuccessful())
                            {
                                Toast.makeText(ResetPasswordActivity.this,"Please check your email, reset password link has been sent.",Toast.LENGTH_LONG).show();
                                startActivity(new Intent(ResetPasswordActivity.this,SignInActivity.class));
                            }

                            else
                            {
                                String message= task.getException().getMessage();
                                Toast.makeText(ResetPasswordActivity.this,"Error Occured "+ message,Toast.LENGTH_LONG).show();
                            }


                        }
                    });
                }

            }
        });

    }
}
