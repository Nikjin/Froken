package com.example.confesso;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegUsernameActivity extends AppCompatActivity {

    EditText editusername;
    Button butCheck;
    TextView textsignin,textProceed;
    ImageView imageCheck;
    Query reference;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg_username);

        editusername=findViewById(R.id.editusername);
        butCheck=findViewById(R.id.butCheck);
        textsignin=findViewById(R.id.textsignin);
        imageCheck=findViewById(R.id.imageCheck);
        textProceed=findViewById(R.id.textProceed);

        imageCheck.setVisibility(View.GONE);
        textProceed.setVisibility(View.GONE);


        butCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                username= editusername.getText().toString();



                reference = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("username").equalTo(username);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount()>0)
                        {
                            //imageCheck.setVisibility(View.GONE);
                            textProceed.setVisibility(View.GONE);

                            imageCheck.setBackgroundResource(R.drawable.ic_wrong);
                            imageCheck.setVisibility(View.VISIBLE);
                        }

                        else {

                            imageCheck.setBackgroundResource(R.drawable.ic_check);

                            imageCheck.setVisibility(View.VISIBLE);
                            textProceed.setVisibility(View.VISIBLE);


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                closeKeyboard();


            }
        });

        textProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegUsernameActivity.this, SignupActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });

        textsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegUsernameActivity.this,SignInActivity.class));

            }
        });




    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
