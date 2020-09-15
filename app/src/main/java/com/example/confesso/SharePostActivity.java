package com.example.confesso;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.confesso.Model.Post;
import com.example.confesso.Model.User;
import com.example.confesso.Notifications.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SharePostActivity extends AppCompatActivity {

    TextView cat, username, tv_username, tv_title, tv_date;
    EditText edittitle;
    ImageView user_image, publisher_image, post_image, back;
    Button butshare;
    String postid, saveCurrentDate, saveCurrentTime,category,postpublisherid,postpublisher;
    FirebaseUser firebaseUser;

    String CurrentUsername;
    String CurrentUserImage;
    String pImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_post);

        cat = findViewById(R.id.cat);
        username = findViewById(R.id.username);
        tv_username = findViewById(R.id.tv_username);
        tv_title = findViewById(R.id.tv_title);
        tv_date = findViewById(R.id.tv_date);
        edittitle = findViewById(R.id.edittitle);
        user_image = findViewById(R.id.user_image);
        publisher_image = findViewById(R.id.publisher_image);
        post_image = findViewById(R.id.post_image);
        back = findViewById(R.id.back);


        butshare = findViewById(R.id.butshare);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();


        Intent intent = getIntent();
        postid = intent.getStringExtra("postid");
        category = intent.getStringExtra("category");
        postpublisher = intent.getStringExtra("un");




        cat.setText(category);



        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts").child(postid);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);

                tv_title.setText(post.getTitle());
                Glide.with(getApplicationContext()).load(post.getPostimage()).into(post_image);
                tv_username.setText(post.getPublishername());
                tv_date.setText(post.getDate());
                postpublisherid = post.getPublisher();
                pImage = post.getPostimage();

                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users").child(post.getPublisher());
                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        User user = dataSnapshot.getValue(User.class);

                        Glide.with(getApplicationContext()).load(user.getImageurl()).into(publisher_image);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                User user = dataSnapshot.getValue(User.class);

                Glide.with(getApplicationContext()).load(user.getImageurl()).apply(new RequestOptions()
                        .placeholder(R.drawable.circle_profile_holder)
                        .error(R.drawable.ic_froken2)).into(user_image);
                username.setText(user.getUsername());
                CurrentUsername = user.getUsername();
                CurrentUserImage = user.getImageurl();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        butshare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                closeKeyboard();


                SharePost();
                addNotifications1();


            }
        });
    }


   public void SharePost()
    {
        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        saveCurrentTime = currentTime.format(calFordTime.getTime());

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Posting");
        progressDialog.show();



        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Shared Posts");
        String sharedpostid = reference.push().getKey();

        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("publisherpostid",postid);
        hashMap.put("sharedpostid",sharedpostid);
        hashMap.put("title",edittitle.getText().toString());
        hashMap.put("category",category);
        hashMap.put("publisher",firebaseUser.getUid());
        hashMap.put("date", saveCurrentDate);
        hashMap.put("time", saveCurrentTime);
        hashMap.put("publishername", username.getText().toString());
        hashMap.put("postpublisherid", postpublisherid);
        hashMap.put("isanonymous",false);


        reference.child(sharedpostid).setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    Toast.makeText(SharePostActivity.this,"Posted Successfully!",Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                  //  startActivity(new Intent(SharePostActivity.this,HomeActivity.class));
                    finish();
                }

                else {
                    Toast.makeText(SharePostActivity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    private void addNotifications1 ()
    {
        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        saveCurrentTime = currentTime.format(calFordTime.getTime());

        if (!postpublisher.equals(firebaseUser.getUid())) {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(postpublisher);
            HashMap<String, Object> hashMap = new HashMap<>();

            hashMap.put("userid", firebaseUser.getUid());
            hashMap.put("text", "shared your post on their profile");
            hashMap.put("postid", postid);
            hashMap.put("ispost", true);
            hashMap.put("isapost", false);
            hashMap.put("time", saveCurrentTime);

            reference.push().setValue(hashMap);

            prepareNotifications(""+postid,
                    ""+CurrentUsername,
                    "Shared your post on their profile",
                    "ShareNotification",
                    ""+postpublisher,
                    ""+pImage,
                    ""+CurrentUserImage);




        }
    }

    private void prepareNotifications(final String pId, final String title, final String description, final String notifcationType, final String hisUid, final String pImage, final String senderImage)
    {
        DatabaseReference allTokens = FirebaseDatabase.getInstance().getReference("Tokens");
        Query query = allTokens.orderByKey().equalTo(hisUid);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Token token = ds.getValue(Token.class);
                    String NOTIFICATION_TITLE = title;
                    String NOTIFICATION_MESSAGE = description;
                    String NOTIFICATION_TYPE = notifcationType;

                    //prepare json what to send, where to send
                    JSONObject notificationJo = new JSONObject();
                    JSONObject notificationBodyJo= new JSONObject();

                    try {
                        notificationBodyJo.put("notificationType",NOTIFICATION_TYPE);
                        notificationBodyJo.put("sender",firebaseUser.getUid());
                        notificationBodyJo.put("pId",pId);
                        notificationBodyJo.put("pTitle" ,NOTIFICATION_TITLE);
                        notificationBodyJo.put("pDescription",NOTIFICATION_MESSAGE);
                        notificationBodyJo.put("hisUid",hisUid);
                        notificationBodyJo.put("pImage",pImage);
                        notificationBodyJo.put("senderImage",senderImage);





                        notificationJo.put("to", token.getToken());
                        notificationJo.put("data", notificationBodyJo);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    sendNotification(notificationJo);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }


    private void sendNotification(JSONObject notificationJo)
    {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://fcm.googleapis.com/fcm/send", notificationJo,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("FCM_RESPONSE","onResponse" + response.toString() );



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(SharePostActivity.this,""+ error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String,String>headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                headers.put("Authorization","key=AAAAse1rt0M:APA91bHMzH6AUIqM40WH6zWIiUjd_ruUmzwtSZ2S5-YFx230PyM6awlU-UTwnE2slGPrkF5Jc_v_QPUg_3-pPXWGmW3QCeYBxxk5Osj_aI1Oo_oXmfTaRRXSF0gNNe0Ay4eo4bxGEXxZ");

                return headers;
            }
        };

        //enqeue the volley
        Volley.newRequestQueue(SharePostActivity.this).add(jsonObjectRequest);

    }



    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

}

