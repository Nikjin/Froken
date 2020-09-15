package com.example.confesso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    private static final String TAG = "";
    EditText email,password;
    Button signin;
    TextView txt_signup,textForget;

    FirebaseAuth auth;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        email = findViewById(R.id.editUsername);
        password =findViewById(R.id.editPass);
        signin = findViewById(R.id.butSignin);
        txt_signup=findViewById(R.id.textSignup);
        textForget=findViewById(R.id.textForget);


        auth= FirebaseAuth.getInstance();

        txt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignInActivity.this,RegUsernameActivity.class));
            }
        });

        textForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(SignInActivity.this,ResetPasswordActivity.class));

            }
        });



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null && auth.getCurrentUser().isEmailVerified() ) {
            // User is signed in
            Intent i = new Intent(SignInActivity.this, HomeActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            Log.d(TAG, "onAuthStateChanged:signed_out");
        }

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog pd = new ProgressDialog(SignInActivity.this);
                pd.setMessage("Please wait...");
                pd.show();

                String str_email=email.getText().toString();
                String str_password= password.getText().toString();

                if (TextUtils.isEmpty(str_email)||TextUtils.isEmpty(str_password))
                {
                    Toast.makeText(SignInActivity.this,"All field are required",Toast.LENGTH_SHORT).show();;

                }



                else
                {
                    auth.signInWithEmailAndPassword(str_email,str_password)
                            .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful())
                                    {
                                        if (auth.getCurrentUser().isEmailVerified()) {
                                            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Users")
                                                    .child(auth.getCurrentUser().getUid());

                                            reference.addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    pd.dismiss();
                                                    Intent intent = new Intent(SignInActivity.this, HomeActivity.class);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                    pd.dismiss();

                                                }
                                            });
                                        }

                                        else {
                                            pd.dismiss();
                                            Toast.makeText(SignInActivity.this,"Verify Your email first.",Toast.LENGTH_LONG).show();


                                        }

                                    }
                                    else
                                    {
                                        pd.dismiss();
                                        Toast.makeText(SignInActivity.this,"Authentication Failed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

    }
}
