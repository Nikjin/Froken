package com.example.confesso.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.confesso.AnonymousCommentsActivity;
import com.example.confesso.CommentsActivity;
import com.example.confesso.FollowersActivity;
import com.example.confesso.Fragment.ProfileFragment;
import com.example.confesso.Model.AnonymousPost;
import com.example.confesso.Model.Post;
import com.example.confesso.Model.SharePost;
import com.example.confesso.Model.User;
import com.example.confesso.Notifications.Token;
import com.example.confesso.R;
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

import static com.example.confesso.PostActivity.TAG;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ViewHolder> {

    public Context mContext;
    public List<SharePost> mPost;
    public List<SharePost> mPostFiltered;


    String str_title,str_desc;
    long difference;
    String  saveCurrentTime;

    String CurrentUsername;
    String CurrentUserImage;
    String postimage;



    private FirebaseUser firebaseUser;

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

    public ShareAdapter(Context mContext, List<SharePost> mPost) {
        this.mContext = mContext;
        this.mPost = mPost;
        this.mPostFiltered = mPost;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.share_item, viewGroup,false);

        return new ShareAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShareAdapter.ViewHolder viewHolder, int i) {

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();

        final SharePost post = mPostFiltered.get(i);





        Glide.with(mContext).load(post.getSharedpostid())
                .apply(new RequestOptions().placeholder(R.drawable.card_bg)).into(viewHolder.post_image);


        str_title=(post.getTitle());
        if (str_title.equals(""))
        {
            viewHolder.title.setVisibility(View.GONE);
        }

        else
        {
            viewHolder.title.setText(post.getTitle());
        }

        viewHolder.date.setText(post.getDate());
        viewHolder.category.setText(post.getCategory());


        publisherInfo(viewHolder.user_image,viewHolder.username,post.getPublisher());


        isLiked(post.getPublisherpostid(),viewHolder.like,viewHolder.likes,viewHolder.comments,viewHolder.flipView);
        nrLikes(viewHolder.likes,post.getPublisherpostid());
        nrComments(viewHolder.comments,post.getPublisherpostid());
        setUpTime(post.getTime(),viewHolder.time);


        if (!post.getIsanonymous())
        {
            sharedpost(post.getPublisherpostid(),viewHolder.tv_date,viewHolder.tv_username,viewHolder.tv_title,viewHolder.publisher_image,post.getPostpublisherid(),viewHolder.post_image);

            viewHolder.r3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, CommentsActivity.class);
               /* intent.putExtra("title",post.getTitle());
                intent.putExtra("description",post.getDescription());
                intent.putExtra("date",post.getDate());
                 */
                    intent.putExtra("postid", post.getPublisherpostid());
                    intent.putExtra("un", post.getPostpublisherid());


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
                    intent.putExtra("postid", post.getPublisherpostid());
                    intent.putExtra("un", post.getPostpublisherid());


                    mContext.startActivity(intent);
                }
            });


        }

        else {

            anonymoussharedpost(post.getPublisherpostid(),viewHolder.tv_date,viewHolder.tv_username,viewHolder.tv_title,viewHolder.publisher_image,post.getPostpublisherid(),viewHolder.post_image);

            viewHolder.r3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AnonymousCommentsActivity.class);
               /* intent.putExtra("title",post.getTitle());
                intent.putExtra("description",post.getDescription());
                intent.putExtra("date",post.getDate());
                 */
                    intent.putExtra("postid", post.getPublisherpostid());
                    intent.putExtra("un", post.getPostpublisherid());


                    mContext.startActivity(intent);
                }
            });

            viewHolder.comment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, AnonymousCommentsActivity.class);
               /* intent.putExtra("title",post.getTitle());
                intent.putExtra("description",post.getDescription());
                intent.putExtra("date",post.getDate());
                intent.putExtra("un",post.getPublisher()); */
                    intent.putExtra("postid", post.getPublisherpostid());
                    intent.putExtra("un", post.getPostpublisherid());


                    mContext.startActivity(intent);
                }
            });
        }





        viewHolder.user_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
                editor.putString("profileid",post.getPublisher());
                editor.apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
            }
        });

        viewHolder.username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = mContext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
                editor.putString("profileid",post.getPublisher());
                editor.apply();

                ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ProfileFragment()).commit();
            }
        });


        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.like.getTag().equals("like"))
                {
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPublisherpostid())
                            .child(firebaseUser.getUid()).setValue(true);

                    addNotifications(post.getPostpublisherid(),post.getPublisherpostid(),post.getIsanonymous());





                }

                else
                {
                    FirebaseDatabase.getInstance().getReference().child("Likes").child(post.getPublisherpostid())
                            .child(firebaseUser.getUid()).removeValue();
                }
            }
        });

        viewHolder.likes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(mContext, FollowersActivity.class);
                intent.putExtra("id",post.getPublisherpostid());
                intent.putExtra("title","likes");
                mContext.startActivity(intent);
            }
        });


        viewHolder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu = new PopupMenu(mContext,v);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId())
                        {
                            case R.id.edit:
                                editPost(post.getSharedpostid());
                                return true;

                            case R.id.delete:
                                FirebaseDatabase.getInstance().getReference("Shared Posts")
                                        .child(post.getSharedpostid()).removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful())
                                                {
                                                    Toast.makeText(mContext,"Deleted!",Toast.LENGTH_SHORT).show();


                                                }
                                            }
                                        });
                                return true;


                            case R.id.report:
                                Toast.makeText(mContext,"Report Clicked!",Toast.LENGTH_SHORT).show();
                                return true;

                            default:
                                return false;

                        }
                    }
                });
                popupMenu.inflate(R.menu.post_menu);
                if (!post.getPublisher().equals(firebaseUser.getUid()))
                {
                    popupMenu.getMenu().findItem(R.id.edit).setVisible(false);
                    popupMenu.getMenu().findItem(R.id.delete).setVisible(false);

                }
                popupMenu.show();

            }
        });





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
        for (SharePost pos : mPost) {
            if (pos.getCategory().equals(category)) {
                mPostFiltered.add(pos);
            }
        }
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView user_image,publisher_image,post_image,comment,more,like;
        public TextView username,likes,tv_username,tv_title,tv_date,category,time,date,title,comments;
        RelativeLayout r3;
        private EasyFlipView flipView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user_image=itemView.findViewById(R.id.user_image);
            post_image=itemView.findViewById(R.id.post_image);
            like=itemView.findViewById(R.id.like);
            comment=itemView.findViewById(R.id.comment);
            publisher_image=itemView.findViewById(R.id.publisher_image);
            tv_username=itemView.findViewById(R.id.tv_username);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_date=itemView.findViewById(R.id.tv_date);
            date=itemView.findViewById(R.id.date);
            time=itemView.findViewById(R.id.time);
            more=itemView.findViewById(R.id.more);
            tv_date=itemView.findViewById(R.id.tv_date);
            category=itemView.findViewById(R.id.cat);
            username=itemView.findViewById(R.id.username);
            likes=itemView.findViewById(R.id.likes);
            r3=itemView.findViewById(R.id.r3);
            title=itemView.findViewById(R.id.title);
            comments=itemView.findViewById(R.id.comments);
            flipView=itemView.findViewById(R.id.flipView);






        }
    }

    private void isLiked(String postid, final ImageView imageView, final TextView likes, final TextView comments, final EasyFlipView r1)
    {
        final FirebaseUser firebaseUser =FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(firebaseUser.getUid()).exists())
                {
                    imageView.setImageResource(R.drawable.ic_liked);
                    imageView.setTag("liked");
                    likes.setVisibility(View.VISIBLE);
                    comments.setVisibility(View.VISIBLE);

                    if(r1.isFrontSide())
                    {
                        r1.flipTheView();
                    }

                }
                else
                {
                    imageView.setImageResource(R.drawable.ic_like);
                    imageView.setTag("like");
                    likes.setVisibility(View.INVISIBLE);
                    comments.setVisibility(View.INVISIBLE);

                    if (r1.isBackSide())
                    {
                        r1.flipTheView();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void addNotifications(String userid , String postid, Boolean isAnonymous)
    {
        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        saveCurrentTime = currentTime.format(calFordTime.getTime());


       /* DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        reference1.addListenerForSingleValueEvent(new ValueEventListener() {
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

        */


        if (!userid.equals(firebaseUser.getUid())) {


            if (!isAnonymous) {


             /*   DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Posts").child(postid);

                ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        Post post = dataSnapshot.getValue(Post.class);
                        postimage = post.getPostimage();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


              */
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(userid);
                HashMap<String, Object> hashMap = new HashMap<>();

                hashMap.put("userid", firebaseUser.getUid());
                hashMap.put("text", "liked your post");
                hashMap.put("postid", postid);
                hashMap.put("ispost", true);
                hashMap.put("isapost", false);
                hashMap.put("time", saveCurrentTime);

                reference.push().setValue(hashMap);

               /* prepareNotifications(""+postid,
                        ""+CurrentUsername,
                        "Liked your post",
                        "LikeNotification",
                        ""+userid,
                        ""+postimage,
                        ""+CurrentUserImage);

                */

            } else {


             /*  DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Anonymous Posts").child(postid);

                ref1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                       AnonymousPost post = dataSnapshot.getValue(AnonymousPost.class);
                        postimage = post.getPostimage();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



              */

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(userid);
                    HashMap<String, Object> hashMap = new HashMap<>();

                    hashMap.put("userid", firebaseUser.getUid());
                    hashMap.put("text", "liked your post");
                    hashMap.put("postid", postid);
                    hashMap.put("ispost", false);
                    hashMap.put("isapost", true);
                    hashMap.put("time", saveCurrentTime);

                    reference.push().setValue(hashMap);

                 /*     prepareNotifications(""+postid,
                        ""+CurrentUsername,
                        "Liked your post",
                        "AnonymousLikeNotification",
                        ""+userid,
                        ""+postimage,
                        ""+CurrentUserImage);



                  */


            }

        }

    }


    private void nrLikes(final TextView likes, String postid)
    {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                likes.setText(dataSnapshot.getChildrenCount()+"");

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


    private void publisherInfo(final ImageView image_profile, final TextView username, String userid){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Glide.with(mContext.getApplicationContext()).load(user.getImageurl())
                        .apply(new RequestOptions()
                        .placeholder(R.drawable.circle_profile_holder)
                        .error(R.drawable.frokenpng)).into(image_profile);
                username.setText(user.getUsername());
                // publisher.setText(user.getUsername());



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


    public void sharedpost(final String publishpostid, final TextView tv_date, final TextView tv_username, final TextView tv_title, final ImageView publisher_image, final String postpublisherid, final ImageView post_image)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts").child(publishpostid);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Post post = dataSnapshot.getValue(Post.class);

                tv_date.setText(post.getDate());
                tv_title.setText(post.getTitle());
                Glide.with(mContext).load(post.getPostimage()).into(post_image);




                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users").child(postpublisherid);
                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        User user = dataSnapshot.getValue(User.class);

                        tv_username.setText(user.getUsername());

                        Glide.with(mContext).load(user.getImageurl()).into(publisher_image);


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

    }

    public void anonymoussharedpost(final String publishpostid, final TextView tv_date, final TextView tv_username, final TextView tv_title, final ImageView publisher_image, final String postpublisherid, final ImageView post_image)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Anonymous Posts").child(publishpostid);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                AnonymousPost post = dataSnapshot.getValue(AnonymousPost.class);

                tv_date.setText(post.getDate());
                tv_title.setText(post.getTitle());
                publisher_image.setImageResource(imageIds[post.getPicpos()]);

                Glide.with(mContext).load(post.getPostimage()).into(post_image);




                DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users").child(postpublisherid);
                reference1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        User user = dataSnapshot.getValue(User.class);

                        tv_username.setText(user.getAnonymous());



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

    }

    private void editPost(final String postid)
    {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Edit Title");

        final EditText editText = new EditText(mContext);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
                );

        editText.setLayoutParams(lp);
        alertDialog.setView(editText);

        getText(postid,editText);
        alertDialog.setPositiveButton("Edit",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        HashMap<String,Object>hashMap = new HashMap<>();
                        hashMap.put("title",editText.getText().toString());

                        FirebaseDatabase.getInstance().getReference("Shared Posts")
                                .child(postid).updateChildren(hashMap);
                    }
                });

        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();;
                    }
                });
        alertDialog.show();

    }

    private void getText(String postid, final EditText edittext)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Shared Posts")
                .child(postid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                edittext.setText(dataSnapshot.getValue(SharePost.class).getTitle());
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








}
