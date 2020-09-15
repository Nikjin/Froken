package com.example.confesso.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.confesso.HomeActivity;
import com.example.confesso.Model.AnonymousComment;
import com.example.confesso.Model.User;
import com.example.confesso.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import static com.example.confesso.PostActivity.TAG;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class AnonymousCommentAdapter extends RecyclerView.Adapter<AnonymousCommentAdapter.ViewHolder>{


    private Context mContext;
    private List<AnonymousComment> mComment;
    private String postid;
    long difference;

    private ValueEventListener mDbListener;
    private DatabaseReference reference;


    private FirebaseUser firebaseUser;

    public AnonymousCommentAdapter(Context mContext, List<AnonymousComment> mComment,String postid) {
        this.mContext = mContext;
        this.mComment = mComment;
        this.postid = postid;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view= LayoutInflater.from(mContext).inflate(R.layout.comment_item,viewGroup,false);


        return new AnonymousCommentAdapter.ViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int i) {

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        final AnonymousComment comment = mComment.get(i);

        holder.comment.setText(comment.getComment());
        getUserInfo(holder.image_profile,holder.username,comment.getPublisher());

        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(mContext, HomeActivity.class);
                intent.putExtra("publisherid",comment.getPublisher());
                mContext.startActivity(intent);
            }
        });

        holder.image_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= new Intent(mContext, HomeActivity.class);
                intent.putExtra("publisherid",comment.getPublisher());
                mContext.startActivity(intent);
            }
        });

        holder.upvote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (holder.upvote.getTag().equals("upvote")) {
                    FirebaseDatabase.getInstance().getReference().child("Upvotes").child(comment.getCommentid())
                            .child(firebaseUser.getUid()).setValue(true);


                } else {
                    FirebaseDatabase.getInstance().getReference().child("Upvotes").child(comment.getCommentid())
                            .child(firebaseUser.getUid()).removeValue();

                }


                DatabaseReference reference=FirebaseDatabase.getInstance().getReference()
                        .child("Upvotes")
                        .child(comment.getCommentid());

                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        String upvotes = String.valueOf(dataSnapshot.getChildrenCount());

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Comments").child(comment.getPostid()).child(comment.getCommentid());
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("upvotes", upvotes );

                        reference.updateChildren(hashMap);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (comment.getPublisher().equals(firebaseUser.getUid()))
                {
                    AlertDialog alertDialog =new AlertDialog.Builder(mContext).create();
                    alertDialog.setTitle("Do you want to delete?");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "No",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int i) {

                                    FirebaseDatabase.getInstance().getReference("Comments")
                                            .child(postid).child(comment.getCommentid())
                                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(mContext,"Deleted!",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    dialog.dismiss();
                                }
                            });

                    alertDialog.show();
                }



                return true;
            }
        });

        isUpvoted(comment.getCommentid(),holder.upvote);
        nrUpvotes(holder.upvotes,comment.getCommentid());
        setUpTime(comment.getTime(),holder.time);




    }

    @Override
    public int getItemCount() {
        return mComment.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image_profile,upvote;
        public TextView username,comment,time,upvotes;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image_profile=itemView.findViewById(R.id.image_profile);
            username=itemView.findViewById(R.id.username);
            comment=itemView.findViewById(R.id.comment);
            time=itemView.findViewById(R.id.time);
            upvotes=itemView.findViewById(R.id.upvotes);
            upvote=itemView.findViewById(R.id.upvote);



        }
    }

    private void getUserInfo(final ImageView imageView, final TextView username, String publisherid)
    {
        reference= FirebaseDatabase.getInstance().getReference().child("Users").child(publisherid);

        mDbListener=  reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Glide.with(mContext.getApplicationContext()).load(user.getImageurl())
                        .apply(new RequestOptions()
                        .placeholder(R.drawable.circle_profile_holder)
                        .error(R.drawable.ic_froken2)).into(imageView);
                username.setText(user.getUsername());
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

    private void isUpvoted(final String commentid, final ImageView imageView)
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Upvotes")
                .child(commentid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.child(firebaseUser.getUid()).exists()) {
                    imageView.setImageResource(R.drawable.ic_upvoted);
                    imageView.setTag("upvoted");

                }
                else {
                    imageView.setImageResource(R.drawable.ic_upvote);
                    imageView.setTag("upvote");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void nrUpvotes(final TextView upvotes, String commentid)
    {
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference()
                .child("Upvotes")
                .child(commentid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                upvotes.setText(dataSnapshot.getChildrenCount() +"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

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

    public void onDestroy() {
        if(reference!=null)
        {
            reference.removeEventListener(mDbListener);

        }
    }




}
