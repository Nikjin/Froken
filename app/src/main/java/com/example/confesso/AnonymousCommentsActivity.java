package com.example.confesso;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.confesso.Adapter.AnonymousCommentAdapter;
import com.example.confesso.Model.AnonymousComment;
import com.example.confesso.Model.AnonymousPost;
import com.example.confesso.Model.User;
import com.example.confesso.Notifications.Token;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.hendraanggrian.appcompat.widget.SocialTextView;
import com.hendraanggrian.appcompat.widget.SocialView;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AnonymousCommentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnonymousCommentAdapter anonymouscommentAdapter;
    private List<AnonymousComment> anonymouscommentList;

    List<String>idList;


    EditText addcomment;
    TextView title,username,date,likes,lb,cat1;
    SocialTextView description;
    String tit,desc,dt,lik,postid,uid,postpublisher,saveCurrentTime,saveCurrentTime1;
    ImageView image_profile,ip1,ip2,post_image,back,post,comment_profile,save,like,listen;
    TextToSpeech tts;
    String text;
    String postpublisher1;
    EasyFlipView flipView,flipView1;
    View line;

    ProgressBar progressBar;

    private Boolean mLikedByCurrentUser;
    private StringBuilder mUsers;
    private String mLikesString = "";
    private User mCurrentUser;
    private static final String TAG ="tag" ;

    private RequestQueue requestQueue;
    String CurrentUsername;
    String CurrentUserImage;
    String pImage;
    String pTitle;



    RelativeLayout r2;

    FirebaseUser firebaseUser;

    Integer [] imageIds= {
            R.drawable.froken1,
            R.drawable.froken2,
            R.drawable.froken3,
            R.drawable.froken4,
            R.drawable.froken5,
            R.drawable.froken6,
            R.drawable.froken7,
            R.drawable.froken8,
            R.drawable.froken9,

    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent =getIntent();
        postid = intent.getStringExtra("postid");
        postpublisher = intent.getStringExtra("un");

        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(false);
        recyclerView.setLayoutManager(linearLayoutManager);
        anonymouscommentList= new ArrayList<>();
        anonymouscommentAdapter= new AnonymousCommentAdapter(this,anonymouscommentList,postid);
        recyclerView.setAdapter(anonymouscommentAdapter);
        recyclerView.setVisibility(View.GONE);

        tts=new TextToSpeech(AnonymousCommentsActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if (status!=TextToSpeech.ERROR)
                {
                    tts.setLanguage(Locale.ENGLISH);
                }

            }
        });



        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        username = findViewById(R.id.username);
        date = findViewById(R.id.date);
        likes = findViewById(R.id.likes);
        back=findViewById(R.id.arr);
        addcomment= findViewById(R.id.addcomment);
        post=findViewById(R.id.post);
        comment_profile=findViewById(R.id.comment_profile);
        post_image=findViewById(R.id.post_image);
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        save = findViewById(R.id.save);
        ip1=findViewById(R.id.like_profile1);
        ip2=findViewById(R.id.like_profile2);
        r2=findViewById(R.id.r2);
        like=findViewById(R.id.like);
        lb=findViewById(R.id.lb);
        listen=findViewById(R.id.listen);
        image_profile=findViewById(R.id.image_profile);
        progressBar =findViewById(R.id.progress_circular);

        flipView = findViewById(R.id.flipView);
        cat1 = findViewById(R.id.cat1);
        flipView1 = findViewById(R.id.flipView1);
        line = findViewById(R.id.line1);




        idList = new ArrayList<>();


        //THIS IS PUBLISHER DETAILS FUNCTION!!
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Anonymous Posts").child(postid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AnonymousPost post = dataSnapshot.getValue(AnonymousPost.class);

                postpublisher1 = post.getPublisher();
                cat1.setText("Category: "+post.getCategory());

                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(postpublisher1);
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        username.setText(user.getAnonymous());
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




        getAnonymousPostDetails();
        //getPostPublisher();
        getLikes();
        getCurrentUser();

        listen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                listenPost();

            }
        });


        isSaved(postid, save);

        description.setOnMentionClickListener(new SocialView.OnClickListener() {
            @Override
            public void onClick(@NonNull SocialView view, @NonNull final CharSequence text) {

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
                reference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                        {
                            User user=snapshot.getValue(User.class);
                            if (user.getUsername().equals(text.toString())) {
                                String userid = user.getId();
                                Intent intent = new Intent(AnonymousCommentsActivity.this, HomeActivity.class);
                                intent.putExtra("publisherid", userid);

                                startActivity(intent);

                            }



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (save.getTag().equals("save"))
                {
                    FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid())
                            .child(postid).setValue(true);
                }
                else
                {
                    FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid())
                            .child(postid).removeValue();
                }
            }
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    finish();
            }
        });

        r2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), FollowersActivity.class);
                intent.putExtra("id", postid);
                intent.putExtra("title", "likes");
                startActivity(intent);

            }
        });


        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (like.getTag().equals("like"))
                {
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(postid)
                            .child(firebaseUser.getUid()).setValue(true);

                    addNotifications1(postpublisher1,postid);

                    prepareNotifications(""+postid,
                            ""+CurrentUsername,
                            "Liked your post",
                            "AnonymousLikeNotification",
                            ""+postpublisher1,
                            ""+pImage,
                            ""+CurrentUserImage);
                }

                else
                {
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(postid)
                            .child(firebaseUser.getUid()).removeValue();
                }

                getCurrentUser();
                getLikes();

            }
        });




        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(addcomment.getText().toString().equals(""))
                {
                    Toast.makeText(AnonymousCommentsActivity.this,"You can't send empty comment",Toast.LENGTH_SHORT).show();

                }
                else {
                    addComment();
                }
            }
        });

        getImage();
        readComments();

        getLikes();

        isSaved(postid, save);

        isLiked(postid,like,flipView,flipView1,recyclerView,line);


        progressBar.setVisibility(View.GONE);



    }

    protected void onDestroy() {

        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }

        super.onDestroy();
        anonymouscommentAdapter.onDestroy();

    }


    private void addComment()
    {

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        saveCurrentTime1 = currentTime.format(calFordTime.getTime());


        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Comments").child(postid);
        String commentid = reference.push().getKey();


        HashMap<String,Object> hashMap = new HashMap<>();
        hashMap.put("comment",addcomment.getText().toString());
        hashMap.put("publisher",firebaseUser.getUid());
        hashMap.put("commentid",commentid);
        hashMap.put("time", saveCurrentTime1);
        hashMap.put("postid", postid);
        hashMap.put("upvotes", "0");




        reference.child(commentid).setValue(hashMap);

        addNotifications();

        prepareNotifications(""+postid,
                ""+CurrentUsername,
                "Commented: "+addcomment.getText().toString(),
                "AnonymousCommentNotification",
                ""+postpublisher1,
                ""+pImage,
                ""+CurrentUserImage);


        addcomment.setText("");
        closeKeyboard();
    }

    private void addNotifications()
    {
        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        saveCurrentTime = currentTime.format(calFordTime.getTime());

        if (!postpublisher1.equals(firebaseUser.getUid())) {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(postpublisher1);
            HashMap<String, Object> hashMap = new HashMap<>();

            hashMap.put("userid", firebaseUser.getUid());
            hashMap.put("text", "commented: " + addcomment.getText().toString());
            hashMap.put("postid", postid);
            hashMap.put("ispost", false);
            hashMap.put("isapost", true);
            hashMap.put("time", saveCurrentTime);


            reference.push().setValue(hashMap);

        }
    }

    private void addNotifications1(String userid , String postid)
    {
        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        saveCurrentTime = currentTime.format(calFordTime.getTime());

        if (!postpublisher1.equals(firebaseUser.getUid())) {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(userid);
            HashMap<String, Object> hashMap = new HashMap<>();

            hashMap.put("userid", firebaseUser.getUid());
            hashMap.put("text", "liked your post");
            hashMap.put("postid", postid);
            hashMap.put("ispost", false);
            hashMap.put("isapost", true);
            hashMap.put("time", saveCurrentTime);


            reference.push().setValue(hashMap);

        }
    }

   /* private void getPostPublisher()
    {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(postpublisher1);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                username.setText(user.getAnonymous());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    */

    private void getImage()
    {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Glide.with(getApplicationContext()).load(user.getImageurl()).apply(new RequestOptions()
                        .placeholder(R.drawable.circle_profile_holder)
                        .error(R.drawable.ic_froken2)).into(comment_profile);
                CurrentUsername =  user.getUsername();
                CurrentUserImage = user.getImageurl();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getAnonymousPostDetails()
    {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Anonymous Posts").child(postid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AnonymousPost post = dataSnapshot.getValue(AnonymousPost.class);
                Glide.with(getApplicationContext()).load(post.getPostimage()).into(post_image);
               // Glide.with(getApplicationContext()).load(imageIds[post.getPicpos()]).into(image_profile);
                image_profile.setImageResource(imageIds[post.getPicpos()]);
                pTitle = post.getTitle();
                pImage = post.getPostimage();

                if(TextUtils.isEmpty(post.getTitle()))
                {
                    title.setVisibility(View.GONE);
                }
                else
                {
                    title.setText(post.getTitle());
                }

                if (TextUtils.isEmpty(post.getDescription()))
                {
                    description.setVisibility(View.GONE);
                }
                else
                {
                    description.setText(post.getDescription());
                    text = title.getText().toString() + ". " + ". " + ". " + description.getText().toString();


                }
                date.setText(post.getDate());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void listenPost()
    {

        //tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);

        if (!tts.isSpeaking())
        {
            listen.setImageResource(R.drawable.ic_listen);


            tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);

        }
        else {
            listen.setImageResource(R.drawable.ic_notlisten);

            tts.stop();
        }


    }





    private void readComments()
    {
        Query reference = FirebaseDatabase.getInstance().getReference("Comments").child(postid).orderByChild("upvotes");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                anonymouscommentList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    AnonymousComment comment= snapshot.getValue(AnonymousComment.class);
                    anonymouscommentList.add(comment);
                }

                anonymouscommentAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void isSaved(final String postid, final ImageView imageView)
    {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Saves")
                .child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(postid).exists())
                {
                    imageView.setImageResource(R.drawable.ic_save_black);
                    imageView.setTag("saved");

                }
                else {
                    imageView.setImageResource(R.drawable.ic_save);
                    imageView.setTag("save");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void getLikes() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Likes")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                idList.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    idList.add(snapshot.getKey());
                }

                if (idList.size()==0)
                {
                    //r2.setVisibility(View.GONE);

                    lb.setVisibility(View.GONE);
                    likes.setVisibility(View.GONE);


                    ip1.setVisibility(View.GONE);
                    ip2.setVisibility(View.GONE);
                }
                else if (idList.size()==1)
                {
                    lb.setVisibility(View.VISIBLE);
                    likes.setVisibility(View.VISIBLE);


                    ip1.setVisibility(View.VISIBLE);
                    showip1();

                    ip2.setVisibility(View.GONE);
                }
                else
                {
                    lb.setVisibility(View.VISIBLE);
                    likes.setVisibility(View.VISIBLE);


                    ip1.setVisibility(View.VISIBLE);
                    ip2.setVisibility(View.VISIBLE);

                    showip1();
                    showip1();
                    showip2();


                }




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void showip1()
    {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(idList.get(0));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Glide.with(getApplicationContext()).load(user.getImageurl()).apply(new RequestOptions()
                        .placeholder(R.drawable.circle_profile_holder)
                        .error(R.drawable.ic_froken2)).into(ip1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private void showip2()
    {
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(idList.get(1));
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Glide.with(getApplicationContext()).load(user.getImageurl()).apply(new RequestOptions()
                        .placeholder(R.drawable.circle_profile_holder)
                        .error(R.drawable.ic_froken2)).into(ip2);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getLikesString(){
        Log.d(TAG, "getLikesString: getting likes string");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child("Likes")
                .child(postid);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mUsers = new StringBuilder();
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
                    Query query = reference
                            .child("Users")
                            .orderByChild("id")
                            .equalTo(singleSnapshot.getKey());
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                                Log.d(TAG, "onDataChange: found like: " +
                                        singleSnapshot.getValue(User.class).getUsername());

                                mUsers.append(singleSnapshot.getValue(User.class).getUsername());
                                mUsers.append(",");
                            }

                            String[] splitUsers = mUsers.toString().split(",");

                            if(mUsers.toString().contains(mCurrentUser.getUsername() + ",")){//mitch, mitchell.tabian
                                mLikedByCurrentUser = true;
                            }else{
                                mLikedByCurrentUser = false;
                            }

                            int length = splitUsers.length;
                            if(length == 1){
                                mLikesString = "" + splitUsers[0];
                            }
                            else if(length == 2){
                                mLikesString = "" + splitUsers[0]
                                        + " and " + splitUsers[1];
                            }
                            else if(length == 3){
                                mLikesString = "" + splitUsers[0]
                                        + ", " + splitUsers[1]
                                        + " and " + splitUsers[2];

                            }
                            else if(length == 4){
                                mLikesString = "" + splitUsers[0]
                                        + ", " + splitUsers[1]
                                        + ", " + splitUsers[2]
                                        + " and " + splitUsers[3];
                            }
                            else if(length > 4){
                                mLikesString = "" + splitUsers[0]
                                        + ", " + splitUsers[1]
                                        + ", " + splitUsers[2]
                                        + " and " + (splitUsers.length - 3) + " others";
                            }
                            Log.d(TAG, "onDataChange: liked string: " + mLikesString);
                            likes.setText(mLikesString);

                        }




                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                if(!dataSnapshot.exists()){
                    Log.d(TAG, "onDataChange: datasnapshot not found" + mLikesString);

                    mLikesString = "";
                    mLikedByCurrentUser = false;
                    likes.setText(mLikesString);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void getCurrentUser(){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Users")
                .orderByChild("id")
                .equalTo(FirebaseAuth.getInstance().getCurrentUser().getUid());;
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for ( DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                    mCurrentUser = singleSnapshot.getValue(User.class);
                }
                getLikesString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: query cancelled.");
            }
        });
    }


    private void isLiked(String postid, final ImageView imageView,final EasyFlipView f1,final EasyFlipView f2,final RecyclerView recyclerView,final View r2)
    {
        final FirebaseUser firebaseUser =FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) r2.getLayoutParams();


                if (dataSnapshot.child(firebaseUser.getUid()).exists())
                {
                    imageView.setImageResource(R.drawable.ic_liked);
                    imageView.setTag("liked");

                    recyclerView.setVisibility(View.VISIBLE);
                    layoutParams.bottomMargin = 0;



                    if(f1.isFrontSide())
                    {
                        f1.flipTheView();
                    }
                    if(f2.isFrontSide())
                    {
                        f2.flipTheView();
                    }
                }
                else
                {
                    imageView.setImageResource(R.drawable.ic_like);
                    imageView.setTag("like");
                    recyclerView.setVisibility(View.GONE);
                    layoutParams.bottomMargin = 140;


                    if (f1.isBackSide()) {
                        f1.flipTheView();

                    }
                    if (f2.isBackSide()) {
                        f2.flipTheView();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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

                        Toast.makeText(AnonymousCommentsActivity.this,""+ error.toString(), Toast.LENGTH_SHORT).show();

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
        Volley.newRequestQueue(AnonymousCommentsActivity.this).add(jsonObjectRequest);

    }


    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}
