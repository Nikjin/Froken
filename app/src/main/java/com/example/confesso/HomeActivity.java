package com.example.confesso;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.confesso.Fragment.BookmarkFragment;
import com.example.confesso.Fragment.HomeFragment;
import com.example.confesso.Fragment.NotificationFragment;
import com.example.confesso.Fragment.ProfileFragment;
import com.example.confesso.Fragment.SearchFragment;
import com.example.confesso.Model.User;
import com.example.confesso.Notifications.Token;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

public class HomeActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment=null;
    TextView topname;
    ImageView options;

    AppBarLayout topApp;
    Toolbar topTool;
    RelativeLayout topRel;


    String eventReceived;
    String tag = "MessengerLoggingService";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.i(tag, "Service Started");



        bottomNavigationView=findViewById(R.id.bottom_navigation);
        topname=findViewById(R.id.topname);
        options=findViewById(R.id.options);
        topApp=findViewById(R.id.topApp);
        topTool=findViewById(R.id.topTool);
        topRel=findViewById(R.id.topRel);



        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(getApplicationContext(), OptionActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });


        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        Bundle intent = getIntent().getExtras();
        if(intent!=null)
        {
            String publisher = intent.getString("publisherid");

            SharedPreferences.Editor editor= getSharedPreferences("PREFS",MODE_PRIVATE).edit();
            editor.putString("profileid",publisher);
            editor.apply();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                    ,new ProfileFragment()).commit();
        }
        else {

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                    ,new HomeFragment()).commit();
        }


        UpdateToken();


    }

    private void UpdateToken(){
        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        String refreshToken= FirebaseInstanceId.getInstance().getToken();
        Token mToken= new Token(refreshToken);
        FirebaseDatabase.getInstance().getReference("Tokens").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(mToken);
    }

    private void userDetail()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);

                topname.setText(user.getUsername());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }





    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
          new BottomNavigationView.OnNavigationItemSelectedListener() {
              @Override
              public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                  switch (menuItem.getItemId()){

                      case R.id.nav_home:
                          selectedFragment = new HomeFragment();
                          topname.setText("Latest Feed");

                          break;

                      case R.id.nav_search:

                          selectedFragment = new SearchFragment();
                          topname.setText("Explore");



                          break;

                      case R.id.nav_bookmark:

                          selectedFragment = new BookmarkFragment();
                          topname.setText("Saved Posts");



                          break;

                      case R.id.nav_profile:

                          SharedPreferences.Editor editor=getSharedPreferences("PREFS",MODE_PRIVATE).edit();
                          editor.putString("profileid", FirebaseAuth.getInstance().getCurrentUser().getUid());
                          editor.apply();
                          selectedFragment=new ProfileFragment();

                          topApp.setBackgroundResource(R.drawable.toolbar_bg);
                          topTool.setBackgroundResource(R.drawable.toolbar_bg);
                          topRel.setBackgroundResource(R.drawable.toolbar_bg);


                          userDetail();
                          break;

                      case R.id.nav_notification:

                          selectedFragment=new NotificationFragment();
                          topname.setText("Notifications");



                          break;

                  }


                  if (selectedFragment!=null)
                  {
                      getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container
                      ,selectedFragment).commit();


                  }




                  return true;


              }
          };




}
