package com.example.confesso.Adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.confesso.CommentsActivity;
import com.example.confesso.EditPostsActivity;
import com.example.confesso.FollowersActivity;
import com.example.confesso.Fragment.ProfileFragment;
import com.example.confesso.Model.Post;
import com.example.confesso.Model.User;
import com.example.confesso.Notifications.Token;
import com.example.confesso.R;
import com.example.confesso.SharePostActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.example.confesso.PostActivity.TAG;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    public Context mContext;
    public List<Post> mPost;
    public List<Post> mPostFiltered;

    private ShareAdapter shareAdapter;

    String str_title, str_desc;
    long difference;
    String saveCurrentTime;

    String CurrentUsername;
    String CurrentUserImage;

    private FirebaseStorage mStorage;
    private ValueEventListener mDBlistener;
    private DatabaseReference reference;


    private RequestQueue requestQueue;

    private FirebaseUser firebaseUser;

    public PostAdapter(Context mContext, List<Post> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
        this.mPostFiltered = mPost;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.post_item, viewGroup, false);


        return new PostAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mStorage = FirebaseStorage.getInstance();
        requestQueue = Volley.newRequestQueue(mContext);

        final Post post = mPostFiltered.get(i);


        Glide.with(mContext).load(post.getPostimage())
                .apply(new RequestOptions().placeholder(R.drawable.placeholder)).into(viewHolder.post_image);

        // viewHolder.description.setText(post.getDescription());


        title(viewHolder.title, post.getTitle());


        viewHolder.date.setText(post.getDate());
        viewHolder.category.setText(post.getCategory());
        viewHolder.cat1.setText(post.getCategory());


        viewHolder.time.setText(post.getTime());


     /*  if (post.getDescription().equals("")){
            viewHolder.description.setVisibility(View.GONE);

        }
        else
        {
          viewHolder.description.setVisibility(View.VISIBLE);


        }*/

        publisherInfo(viewHolder.image_profile, viewHolder.username, post.getPublisher(), viewHolder.save, viewHolder.share,viewHolder.shares);


        isLiked(post.getPostid(), viewHolder.like,viewHolder.likes,viewHolder.comments,viewHolder.shares,viewHolder.flipView);
        nrLikes(viewHolder.likes, post.getPostid());
        nrComments(viewHolder.comments,post.getPostid());
        nrShares(viewHolder.shares,post.getPostid());

        isSaved(post.getPostid(), viewHolder.save);
        setUpTime(post.getTime(), viewHolder.time);


        viewHolder.image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("profileid", post.getPublisher());
                editor.apply();

                ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
            }
        });

        viewHolder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS", Context.MODE_PRIVATE).edit();
                editor.putString("profileid", post.getPublisher());
                editor.apply();

                ((FragmentActivity) mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
            }
        });


        viewHolder.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.save.getTag().equals("save")) {
                    FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid())
                            .child(post.getPostid()).setValue(true);
                } else {
                    FirebaseDatabase.getInstance().getReference().child("Saves").child(firebaseUser.getUid())
                            .child(post.getPostid()).removeValue();
                }
            }
        });


            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    CurrentUsername =  user.getUsername();
                    CurrentUserImage = user.getImageurl();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



            viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewHolder.like.getTag().equals("like")) {
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostid())
                            .child(firebaseUser.getUid()).setValue(true);

                    addNotifications(post.getPublisher(), post.getPostid());


                    prepareNotifications(""+post.getPostid(),
                            ""+CurrentUsername,
                             "Liked your post",
                            "LikeNotification",
                            ""+post.getPublisher(),
                            ""+post.getPostimage(),
                            ""+CurrentUserImage);

                } else {
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPostid())
                            .child(firebaseUser.getUid()).removeValue();
                }
            }
        });


        //anonymous mei dalna hai jab tak dusra "//" nahi aa jata
        viewHolder.post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommentsActivity.class);
               /* intent.putExtra("title",post.getTitle());
                intent.putExtra("description",post.getDescription());
                intent.putExtra("date",post.getDate());
                 */
                intent.putExtra("postid", post.getPostid());
                intent.putExtra("un", post.getPublisher());


                mContext.startActivity(intent);
            }
        });

        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CommentsActivity.class);
               /* intent.putExtra("title",post.getTitle());
                intent.putExtra("description",post.getDescription());
                intent.putExtra("date",post.getDate());
                intent.putExtra("un",post.getPublisher()); */
                intent.putExtra("postid", post.getPostid());
                intent.putExtra("un", post.getPublisher());


                mContext.startActivity(intent);
            }
        });


        viewHolder.likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, FollowersActivity.class);
                intent.putExtra("id", post.getPostid());
                intent.putExtra("title", "likes");
                mContext.startActivity(intent);
            }
        });

        viewHolder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SharePostActivity.class);
                intent.putExtra("postid", post.getPostid());
                intent.putExtra("category", post.getCategory());
                intent.putExtra("un", post.getPublisher());


                mContext.startActivity(intent);

            }
        });

        viewHolder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(mContext, v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {
                            case R.id.edit:
                                Intent intent = new Intent(mContext, EditPostsActivity.class);
                                intent.putExtra("postid", post.getPostid());
                                mContext.startActivity(intent);
                                return true;

                            case R.id.delete:

                                final ProgressDialog progressDialog = new ProgressDialog(mContext);
                                progressDialog.setMessage("Deleting");
                                progressDialog.show();

                                //Shared Posts remove krenge
                                FirebaseDatabase.getInstance().getReference("Shared Posts")
                                        .addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                                                {
                                                    if (snapshot.child("publisherpostid").getValue().toString().equals(post.getPostid()))
                                                    {
                                                        // shared post delete krenge
                                                        FirebaseDatabase.getInstance().getReference("Shared Posts")
                                                                .child(snapshot.getKey()).removeValue()
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if (task.isSuccessful()) {
                                                                            makeText(mContext, "Deleted!", LENGTH_SHORT).show();

                                                                        }
                                                                    }
                                                                });

                                                    }

                                                }

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                FirebaseDatabase.getInstance().getReference("Notifications")
                                        .child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren())
                                        {
                                            //Key lenge yaha pe post ki
                                            if (snapshot.child("postid").getValue().toString().equals(post.getPostid()))
                                            {
                                                // ab uss key se notification delete krenge phle
                                                FirebaseDatabase.getInstance().getReference("Notifications")
                                                        .child(firebaseUser.getUid()).child(snapshot.getKey()).removeValue()
                                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<Void> task) {
                                                                if (task.isSuccessful()) {
                                                                    makeText(mContext, "Deleted!", LENGTH_SHORT).show();

                                                                }
                                                            }
                                                        });

                                            }


                                        }


                                        // ab finally post delete hoga
                                        FirebaseDatabase.getInstance().getReference("Posts")
                                                .child(post.getPostid()).removeValue()
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            makeText(mContext, "Deleted!", LENGTH_SHORT).show();
                                                            StorageReference deleteFile = mStorage.getReferenceFromUrl(post.getPostimage());
                                                            deleteFile.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                                @Override
                                                                public void onSuccess(Void aVoid) {
                                                                }
                                                            });
                                                            notifyDataSetChanged();
                                                            progressDialog.dismiss();


                                                        }
                                                    }
                                                });



                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });





                                return true;


                            case R.id.report:
                                makeText(mContext, "Report Clicked!", LENGTH_SHORT).show();
                                return true;

                            default:
                                return false;

                        }
                    }
                });
                popupMenu.inflate(R.menu.post_menu);
                if (!post.getPublisher().equals(firebaseUser.getUid())) {
                    popupMenu.getMenu().findItem(R.id.edit).setVisible(false);
                    popupMenu.getMenu().findItem(R.id.delete).setVisible(false);

                }
                popupMenu.show();

            }
        });


//

    }

    @Override
    public int getItemCount() {
        return mPostFiltered.size();
    }

    public void filteredu() {
        filtercategory("Education/News");
    }

    public void filterfashion() {
        filtercategory("Fashion/Beauty");
    }

    public void filterfood() {
        filtercategory("Food");
    }

    public void filterfiction() {
        filtercategory("Fiction/Fantasy");
    }

    public void filterfun() {
        filtercategory("Humour/Fun");
    }

    public void filtermusic() {
        filtercategory("Music/Dance");
    }

    public void filtermovie() {
        filtercategory("Movies/TV shows");
    }

    public void filterphoto() {
        filtercategory("Photography/Videography");
    }

    public void filtersci() {
        filtercategory("Science/Tech");
    }

    public void filterstories() {
        filtercategory("Stories/Confessions");
    }

    public void filtersports() {
        filtercategory("Sports/Fitness");
    }

    public void filtertravel() {
        filtercategory("Travel");
    }

    public void filterquote() {
        filtercategory("Thoughts/Quotes");
    }

    public void filterother() {
        filtercategory("Others");
    }

    public void nofilter() {
        // mPostFiltered.clear();
        mPostFiltered = mPost;
        notifyDataSetChanged();

    }


    private void filtercategory(String category) {
        mPostFiltered = new ArrayList<>();
        for (Post pos : mPost) {
            if (pos.getCategory().equals(category)) {
                mPostFiltered.add(pos);
            }
        }
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image_profile, post_image, like, comment, save, share, more;
        private TextView username, likes, publisher, description, title, category, date, time,comments,shares,cat1;
        RelativeLayout container,r1;
        private EasyFlipView flipView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_profile = itemView.findViewById(R.id.image_profile);
            post_image = itemView.findViewById(R.id.post_image);
            like = itemView.findViewById(R.id.like);
            comment = itemView.findViewById(R.id.comment);
            save = itemView.findViewById(R.id.save);
            share = itemView.findViewById(R.id.share);
            title = itemView.findViewById(R.id.title);
            category = itemView.findViewById(R.id.cat);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            more = itemView.findViewById(R.id.more);
            container = itemView.findViewById(R.id.container);
            comments = itemView.findViewById(R.id.comments);
            shares = itemView.findViewById(R.id.shares);
            r1 = itemView.findViewById(R.id.r1);
            cat1 = itemView.findViewById(R.id.cat1);


            flipView = itemView.findViewById(R.id.flipView);




            username = itemView.findViewById(R.id.username);
            likes = itemView.findViewById(R.id.likes);
            //publisher=itemView.findViewById(R.id.publisher);
            // description=itemView.findViewById(R.id.description);


        }
    }


    private void isLiked(final String postid, final ImageView imageView, final TextView likes, final TextView comments, final TextView shares, final EasyFlipView r1) {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.child(firebaseUser.getUid()).exists()) {
                    imageView.setImageResource(R.drawable.ic_liked);
                    imageView.setTag("liked");
                    likes.setVisibility(View.VISIBLE);
                    comments.setVisibility(View.VISIBLE);
                    shares.setVisibility(View.VISIBLE);

                    if(r1.isFrontSide())
                    {
                        r1.flipTheView();
                    }



                }
                else {
                    imageView.setImageResource(R.drawable.ic_like);
                    imageView.setTag("like");
                    likes.setVisibility(View.INVISIBLE);
                    comments.setVisibility(View.INVISIBLE);
                    shares.setVisibility(View.INVISIBLE);

                    if (r1.isBackSide()) {
                        r1.flipTheView();

                    }


                }




            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

    private void addNotifications(String userid, String postid) {
        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        saveCurrentTime = currentTime.format(calFordTime.getTime());

        if (!userid.equals(firebaseUser.getUid())) {

            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(userid);
            HashMap<String, Object> hashMap = new HashMap<>();

            hashMap.put("userid", firebaseUser.getUid());
            hashMap.put("text", "liked your post");
            hashMap.put("postid", postid);
            hashMap.put("ispost", true);
            hashMap.put("isapost", false);
            hashMap.put("time", saveCurrentTime);

            reference.push().setValue(hashMap);
        }
    }

    //below functions are for Push Notification---- Prepare and then Send

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

                        Toast.makeText(mContext,""+ error.toString(), Toast.LENGTH_SHORT).show();

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
        Volley.newRequestQueue(mContext).add(jsonObjectRequest);

    }





    private void nrLikes(final TextView likes, String postid)
    {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                likes.setText(dataSnapshot.getChildrenCount() +"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void nrComments(final TextView comments, String postid)
    {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference()
                .child("Comments")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                comments.setText(dataSnapshot.getChildrenCount() +"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void nrShares(final TextView shares, final String postid)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Shared Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                int count =0;

                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if (snapshot.child("publisherpostid").getValue().toString().equals(postid)) {
                        count = count+1;
                    }

                }
                shares.setText(count +"");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }



    private void publisherInfo(final ImageView image_profile, final TextView username, String userid, final ImageView save, final ImageView share , final TextView shares){
        reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

        mDBlistener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Glide.with(mContext.getApplicationContext()).load(user.getImageurl())
                        .apply(new RequestOptions()
                        .placeholder(R.drawable.circle_profile_holder)
                        .error(R.drawable.ic_froken2)).into(image_profile);
                username.setText(user.getUsername());
               // publisher.setText(user.getUsername());

                if(user.getId().equals(firebaseUser.getUid()))
                {
                    save.setVisibility(View.INVISIBLE);




                }


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
                    imageView.setImageResource(R.drawable.ic_nsave_white);
                    imageView.setTag("saved");

                }
                else {
                    imageView.setImageResource(R.drawable.ic_nsave);
                    imageView.setTag("save");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    private void setUpTime(String date ,TextView time){
        long timestampDiff = getTimestampDifference(date);

        long seconds= MILLISECONDS.toSeconds(timestampDiff);
        long minutes= MILLISECONDS.toMinutes(timestampDiff);
        long hours= MILLISECONDS.toHours(timestampDiff);
        long days=  MILLISECONDS.toDays(timestampDiff);
        int months=  (int)days/30;
        int years=  months/12;

        if(seconds<60)
        {
            time.setText(seconds+" seconds ago");
        }
        else if(minutes<60)
        {
            time.setText(minutes+" minutes ago");
        }
        else if(hours<24)
        {
            time.setText(hours+" hours ago");
        }
        else if(days<30)
        {
            time.setText(days+" days ago");
        }
        else if(months<12) {
            time.setText(months+" months ago");

        }
        else
        {
            time.setText(years+" years ago");

        }

       /* if(!timestampDiff.equals("0")){
            time.setText(timestampDiff + " DAYS AGO");
        }else{
            time.setText("TODAY");
        }*/
    }


    public Long getTimestampDifference(String date) {
        Log.d(TAG, "getTimestampDifference: getting timestamp difference.");

        Calendar c = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));//google 'android list of timezones'

        Date today = c.getTime();


        sdf.format(today);

        Date timestamp;
        final String photoTimestamp = date;

        try{
            timestamp = sdf.parse(photoTimestamp);

            difference = today.getTime() - timestamp.getTime();
        }catch (ParseException e){
            Log.e(TAG, "getTimestampDifference: ParseException: " + e.getMessage() );
            difference = Long.valueOf(0);
        }
        return difference;
    }

    private void title(TextView title, String str_title)
    {

        title.setVisibility(View.VISIBLE);


        if (str_title.equals(""))
        {
            title.setVisibility(View.GONE);
        }

        else
        {
            title.setText(str_title);
        }
    }

    public void onDestroy() {

        if(reference!=null)
        {
            reference.removeEventListener(mDBlistener);

        }
    }







}
