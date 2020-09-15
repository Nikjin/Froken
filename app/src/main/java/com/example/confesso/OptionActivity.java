package com.example.confesso;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.confesso.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class OptionActivity extends AppCompatActivity {

    TextView logout,settings,find,username;
    ImageView back,imgprofile;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        logout = findViewById(R.id.logout);
        settings =findViewById(R.id.settings);
        back =findViewById(R.id.back);
        imgprofile =findViewById(R.id.imgprofile);
        find =findViewById(R.id.find);
        username =findViewById(R.id.username);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();



       back.setOnTouchListener(new View.OnTouchListener() {
           @Override
           public boolean onTouch(View v, MotionEvent event) {
               finish();
               overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);

               return false;
           }
       });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finishAffinity(); //ye updated method hai bc sab previous activities clear kr dega
                startActivity(new Intent(OptionActivity.this, SignInActivity.class));

                //startActivity(new Intent(OptionActivity.this,StartActivity.class)
              //  .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(OptionActivity.this, AccountSettingsActivity.class));

                //startActivity(new Intent(OptionActivity.this,StartActivity.class)
                //  .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
            }
        });

        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OptionActivity.this, FollowersActivity.class);
                intent.putExtra("title","find people");
                startActivity(intent);
            }
        });

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                Glide.with(getApplicationContext()).load(user.getImageurl()).apply(new RequestOptions()
                        .placeholder(R.drawable.circle_profile_holder)
                        .error(R.drawable.ic_froken2)).into(imgprofile);
                username.setText(user.getUsername());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);


    }
}
