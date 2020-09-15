package com.example.confesso;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditAnonymousnameActivity extends AppCompatActivity {

    EditText editusername;
    Button butCheck;
    TextView done;
    ImageView imageCheck,back;
    Query reference;
    String anonymous;

    FirebaseUser firebaseUser;

    int flag=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_anonymousname);

        editusername=findViewById(R.id.editusername);
        butCheck=findViewById(R.id.butCheck);
        done=findViewById(R.id.done);
        imageCheck=findViewById(R.id.imageCheck);
        back=findViewById(R.id.back);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        butCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                anonymous= editusername.getText().toString();



                reference = FirebaseDatabase.getInstance().getReference().child("Users").orderByChild("anonymous").equalTo(anonymous);

                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount()>0)
                        {

                            imageCheck.setBackgroundResource(R.drawable.ic_wrong_purple);
                            imageCheck.setVisibility(View.VISIBLE);
                            flag=0;
                        }

                        else {

                            imageCheck.setBackgroundResource(R.drawable.ic_check_purple);

                            imageCheck.setVisibility(View.VISIBLE);
                            flag=1;



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                closeKeyboard();


            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                closeKeyboard();

                if (flag==1)
                {
                    final ProgressDialog pd = new ProgressDialog(EditAnonymousnameActivity.this);
                    pd.setMessage("Changing anonymous name");
                    pd.show();

                    updateProfile(editusername.getText().toString());

                    pd.dismiss();

                    finish();

                }
                else {
                    Toast.makeText(EditAnonymousnameActivity.this,"Choose correct anonymous name",Toast.LENGTH_SHORT).show();
                }


            }


        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void updateProfile(String anonymous) {

        /* final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Uploading");
        pd.show(); */

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("anonymous",anonymous);
        reference.updateChildren(hashMap);

        //pd.dismiss();

    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}
