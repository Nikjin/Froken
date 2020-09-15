package com.example.confesso;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DeleteAccountActivity extends AppCompatActivity {

    ImageView back;
    Button butCheck;
    ProgressDialog pd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_account);

        back = findViewById(R.id.back);
        butCheck = findViewById(R.id.butCheck);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        butCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pd= new ProgressDialog(DeleteAccountActivity.this);
                pd.setMessage("Please wait...");
                pd.show();

                FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
                if (user!=null)
                {
                    user.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                pd.dismiss();
                                Toast.makeText(getApplicationContext(),"Account deactivated",Toast.LENGTH_SHORT).show();
                                finishAffinity();
                                startActivity(new Intent(DeleteAccountActivity.this,SignInActivity.class));
                            }
                            else {
                                pd.dismiss();
                                Toast.makeText(getApplicationContext(),"Account could not be deactivated",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                }
                else {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(),"Account could not be deactivated",Toast.LENGTH_SHORT).show();

                }


            }
        });



    }
}
