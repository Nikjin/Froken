package com.example.confesso.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.confesso.AnonymousCommentsActivity;
import com.example.confesso.Model.AnonymousPost;
import com.example.confesso.Model.User;
import com.example.confesso.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.ArrayList;
import java.util.List;

public class AnonymousSearchAdapter extends RecyclerView.Adapter<AnonymousSearchAdapter.NewsViewHolder> implements Filterable {


    Context mContext;
    List<AnonymousPost> mData ;
    List<AnonymousPost> mDataFiltered ;
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



    public AnonymousSearchAdapter(Context mContext, List<AnonymousPost> mData) {
        this.mContext = mContext;
        this.mData = mData;
        this.mDataFiltered = mData;

    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.search_item,viewGroup,false);
        return new NewsViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder newsViewHolder, final int position) {

        // bind data here

        // we apply animation to views here
        // first lets create an animation for user photo
        newsViewHolder.img_user.setVisibility(View.INVISIBLE);

        // newsViewHolder.img_user.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_transition_animation));

        // lets create the animation for the whole card
        // first lets create a reference to it
        // you ca use the previous same animation like the following

        // but i want to use a different one so lets create it ..
        newsViewHolder.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fade_scale_animation));



        publisherInfo(newsViewHolder.img_user,newsViewHolder.tv_username,mDataFiltered.get(position).getPublisher());
        newsViewHolder.tv_title.setText(mDataFiltered.get(position).getTitle());
        // newsViewHolder.tv_username.setText(mDataFiltered.get(position).getPublisher());
        newsViewHolder.tv_date.setText(mDataFiltered.get(position).getDate());
        newsViewHolder.img_user.setImageResource(imageIds[mDataFiltered.get(position).getPicpos()]);
        newsViewHolder.category.setText(mDataFiltered.get(position).getCategory());

        Glide.with(mContext).load(mDataFiltered.get(position).getPostimage()).into(newsViewHolder.post_image);

        isLiked(mDataFiltered.get(position).getPostid(),newsViewHolder.flipView,newsViewHolder.img_user);

        newsViewHolder.post_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AnonymousCommentsActivity.class);
               /* intent.putExtra("title",post.getTitle());
                intent.putExtra("description",post.getDescription());
                intent.putExtra("date",post.getDate());
                 */
                intent.putExtra("postid",mDataFiltered.get(position).getPostid());
                intent.putExtra("un",mDataFiltered.get(position).getPublisher());




                mContext.startActivity(intent);
            }
        });

        newsViewHolder.tv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AnonymousCommentsActivity.class);
               /* intent.putExtra("title",post.getTitle());
                intent.putExtra("description",post.getDescription());
                intent.putExtra("date",post.getDate());
                 */
                intent.putExtra("postid",mDataFiltered.get(position).getPostid());
                intent.putExtra("un",mDataFiltered.get(position).getPublisher());




                mContext.startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mDataFiltered.size();
    }


    public void filteredu()
    {
        filtercategory("Education/News");
    }

    public void filterfashion()
    {
        filtercategory("Fashion/Beauty");
    }

    public void filterfood()
    {
        filtercategory("Food");
    }

    public void filterfiction()
    {
        filtercategory("Fiction/Fantasy");
    }

    public void filterfun()
    {
        filtercategory("Humour/Fun");
    }

    public void filtermusic()
    {
        filtercategory("Music/Dance");
    }

    public void filtermovie()
    {
        filtercategory("Movies/TV shows");
    }

    public void filterphoto()
    {
        filtercategory("Photography/Videography");
    }

    public void filtersci()
    {
        filtercategory("Science/Tech");
    }

    public void filterstories()
    {
        filtercategory("Stories/Confessions");
    }

    public void filtersports()
    {
        filtercategory("Sports/Fitness");
    }

    public void filtertravel()
    {
        filtercategory("Travel");
    }

    public void filterquote()
    {
        filtercategory("Thoughts/Quotes");
    }

    public void filterother()
    {
        filtercategory("Others");
    }

    public void nofilter()
    {
        // mPostFiltered.clear();
        mDataFiltered = mData;
        notifyDataSetChanged();

    }


    private void filtercategory(String category) {
        mDataFiltered = new ArrayList<>();
        for(AnonymousPost pos : mData) {
            if(pos.getCategory().equals(category)) {
                mDataFiltered.add(pos);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {

        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String Key = constraint.toString();
                if (Key.isEmpty()) {

                    mDataFiltered = mData ;

                }
                else {
                    List<AnonymousPost> lstFiltered = new ArrayList<>();
                    for (AnonymousPost row : mData) {

                        if (row.getTitle().toLowerCase().contains(Key.toLowerCase())){
                            lstFiltered.add(row);
                        }

                    }

                    mDataFiltered = lstFiltered;

                }


                FilterResults filterResults = new FilterResults();
                filterResults.values= mDataFiltered;
                return filterResults;

            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {


                mDataFiltered = (List<AnonymousPost>) results.values;
                notifyDataSetChanged();

            }
        };




    }

    public class NewsViewHolder extends RecyclerView.ViewHolder {



        TextView tv_username,tv_title,tv_date,category;
        ImageView img_user,post_image;
        RelativeLayout container;
        EasyFlipView flipView;





        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            container = itemView.findViewById(R.id.container);
            tv_username = itemView.findViewById(R.id.tv_username);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_date = itemView.findViewById(R.id.tv_date);
            img_user = itemView.findViewById(R.id.img_user);
            post_image = itemView.findViewById(R.id.post_image);
            category = itemView.findViewById(R.id.category);
            flipView = itemView.findViewById(R.id.flipView);






        }




    }

    private void isLiked(final String postid, final EasyFlipView flipView, final ImageView image)
    {
        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Likes")
                .child(postid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(firebaseUser.getUid()).exists()) {

                    image.setVisibility(View.VISIBLE);

                    if(flipView.isFrontSide())
                    {
                        flipView.flipTheView();
                    }


                } else {

                    image.setVisibility(View.INVISIBLE);

                    if (flipView.isBackSide())
                    {
                        flipView.flipTheView();

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void publisherInfo(final ImageView image_profile, final TextView username, String userid){
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                //Glide.with(mContext).load(user.getImageurl()).into(image_profile);
                username.setText(user.getAnonymous());
                // publisher.setText(user.getUsername());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

