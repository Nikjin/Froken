package com.example.confesso.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.confesso.AnonymousCommentsActivity;
import com.example.confesso.CommentsActivity;
import com.example.confesso.Fragment.ProfileFragment;
import com.example.confesso.Model.AnonymousPost;
import com.example.confesso.Model.Notification;
import com.example.confesso.Model.Post;
import com.example.confesso.Model.User;
import com.example.confesso.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import static com.example.confesso.PostActivity.TAG;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder>{

    private Context mContext;
    private List<Notification>mNotification;

    long difference;


    public NotificationAdapter(Context mContext, List<Notification> mNotification) {
        this.mContext = mContext;
        this.mNotification = mNotification;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.notification_item,parent,false);

        return new NotificationAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {

        final Notification notification = mNotification.get(i);

        holder.text.setText(notification.getText());

        getUserInfo(holder.image_profile,holder.username,notification.getUserid());

        if (notification.isIspost())
        {
            holder.post_image.setVisibility(View.VISIBLE);
            getPostImage(holder.post_image,notification.getPostid());
        }

        else if (notification.isIsapost())
        {
            holder.post_image.setVisibility(View.VISIBLE);
            getAPostImage(holder.post_image,notification.getPostid());
        }
        else
        {
            holder.post_image.setVisibility(View.GONE);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (notification.isIspost())
                {
                    Intent intent = new Intent(mContext, CommentsActivity.class);
               /* intent.putExtra("title",post.getTitle());
                intent.putExtra("description",post.getDescription());
                intent.putExtra("date",post.getDate());
                intent.putExtra("un",post.getPublisher()); */
                    intent.putExtra("postid",notification.getPostid());
                    intent.putExtra("un",notification.getUserid());




                    mContext.startActivity(intent);


                }
                else if (notification.isIsapost())
                {
                    Intent intent = new Intent(mContext, AnonymousCommentsActivity.class);
               /* intent.putExtra("title",post.getTitle());
                intent.putExtra("description",post.getDescription());
                intent.putExtra("date",post.getDate());
                intent.putExtra("un",post.getPublisher()); */
                    intent.putExtra("postid",notification.getPostid());
                    intent.putExtra("un",notification.getUserid());




                    mContext.startActivity(intent);

                }
                else
                {
                    SharedPreferences.Editor editor =  mContext.getSharedPreferences("PREFS",Context.MODE_PRIVATE).edit();
                    editor.putString("profileid",notification.getUserid());
                    editor.apply();

                    ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            new ProfileFragment()).commit();

                }
            }
        });

        setUpTime(notification.getTime(),holder.notitime);

    }

    @Override
    public int getItemCount() {
        return mNotification.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public ImageView image_profile,post_image;
        public TextView username,text,notitime;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_profile=itemView.findViewById(R.id.image_profile);
            post_image=itemView.findViewById(R.id.post_image);

            username=itemView.findViewById(R.id.username);
            text=itemView.findViewById(R.id.comment);
            notitime=itemView.findViewById(R.id.notitime);



        }
    }


    private void getUserInfo(final ImageView imageView , final TextView username, String publisherid)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(publisherid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Glide.with(mContext.getApplicationContext()).load(user.getImageurl()).apply(new RequestOptions()
                        .placeholder(R.drawable.circle_profile_holder)
                        .error(R.drawable.ic_froken2)
                        .fitCenter()).into(imageView);
                username.setText(user.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private void getPostImage(final ImageView imageView, final String postid)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts").child(postid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);
                Glide.with(mContext.getApplicationContext()).load(post.getPostimage()).into(imageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getAPostImage(final ImageView imageView, final String postid)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Anonymous Posts").child(postid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                AnonymousPost post = dataSnapshot.getValue(AnonymousPost.class);
                Glide.with(mContext).load(post.getPostimage()).into(imageView);

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


}
