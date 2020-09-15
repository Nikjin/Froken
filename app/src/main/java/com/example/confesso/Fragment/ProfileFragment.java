package com.example.confesso.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.confesso.Adapter.AnonymousPostAdapter;
import com.example.confesso.Adapter.PostAdapter;
import com.example.confesso.Adapter.ShareAdapter;
import com.example.confesso.EditProfileActivity;
import com.example.confesso.FollowersActivity;
import com.example.confesso.Model.AnonymousPost;
import com.example.confesso.Model.Post;
import com.example.confesso.Model.SharePost;
import com.example.confesso.Model.User;
import com.example.confesso.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class ProfileFragment extends Fragment {

    ImageView image_profile,cover,back;
    TextView posts,followers,following,fullname,name,stalk,country;
    Button edit_profile;
    LinearLayout stalks,mid_bar,llfoll,llfolo;
    TextView bio;
    RecyclerView recyclerView;
    PostAdapter postAdapter;
     List<Post>postList;

    RecyclerView recyclerView_anonymous;
    AnonymousPostAdapter apostAdapter;
     List<AnonymousPost>apostList;

    RecyclerView recyclerView_shared;
    ShareAdapter shareAdapter;
    List<SharePost> shareList;



    FirebaseUser firebaseUser;
    String profileid,saveCurrentTime;

    ImageButton recent,anonymous,shared;
    ImageView location;

    ProgressBar progressBar;

    private ValueEventListener mDblistener;
    private DatabaseReference reference;





    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);



        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        SharedPreferences prefs= getContext().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        profileid = prefs.getString("profileid","none");

        image_profile = view.findViewById(R.id.image_profile);
        posts = view.findViewById(R.id.posts);
        followers = view.findViewById(R.id.followers);
        following = view.findViewById(R.id.followings);
        fullname = view.findViewById(R.id.fullname);
        name = view.findViewById(R.id.name);
        bio = view.findViewById(R.id.bio);
        stalk = view.findViewById(R.id.stalk);
        edit_profile = view.findViewById(R.id.butedit);
        recent = view.findViewById(R.id.recent);
        anonymous = view.findViewById(R.id.anonymous);
        shared = view.findViewById(R.id.shared);
        country = view.findViewById(R.id.place);
        back = view.findViewById(R.id.arr);
        stalks = view.findViewById(R.id.stalks);
        location = view.findViewById(R.id.location);
        mid_bar = view.findViewById(R.id.mid_bar);

        progressBar =view.findViewById(R.id.progress_circular);

        llfoll =view.findViewById(R.id.llfoll);
        llfolo =view.findViewById(R.id.llfolo);




        cover = view.findViewById(R.id.cover);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        postList = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(),postList);
        recyclerView.setAdapter(postAdapter);


        recyclerView_anonymous = view.findViewById(R.id.recycler_view_anonymous);
        recyclerView_anonymous.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager_anonymous= new LinearLayoutManager(getContext());
        recyclerView_anonymous.setLayoutManager(linearLayoutManager_anonymous);
        apostList = new ArrayList<>();
        apostAdapter = new AnonymousPostAdapter(getContext(),apostList);
        recyclerView_anonymous.setAdapter(apostAdapter);

        recyclerView_shared = view.findViewById(R.id.recycler_view_shared);
        recyclerView_shared.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager_shared= new LinearLayoutManager(getContext());
        recyclerView_shared.setLayoutManager(linearLayoutManager_shared);
        shareList = new ArrayList<>();
        shareAdapter = new ShareAdapter(getContext(),shareList);
        recyclerView_shared.setAdapter(shareAdapter);




        recyclerView.setVisibility(View.VISIBLE);
        recyclerView_anonymous.setVisibility(View.GONE);
        recyclerView_shared.setVisibility(View.GONE);



        userInfo();
        getFollowers();
        getNrPosts();
        myFotos();
        myAnonymous();
        getStalks();
        myShared();









        if (profileid.equals(firebaseUser.getUid()))
        {
            edit_profile.setText("Edit");
        }
        else {

            anonymous.setVisibility(View.GONE);
            stalks.setVisibility(View.GONE);


            checkFollow();


        }




        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String btn = edit_profile.getText().toString();

                if (btn.equals("Edit"))
                {
                    startActivity(new Intent(getContext(), EditProfileActivity.class));

                }
                else if (btn.equals("follow"))
                {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(profileid).setValue(true);

                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileid)
                            .child("followers").child(firebaseUser.getUid()).setValue(true);

                    addNotifications();
                }
                else if (btn.equals("following"))
                {
                    FirebaseDatabase.getInstance().getReference().child("Follow").child(firebaseUser.getUid())
                            .child("following").child(profileid).removeValue();

                    FirebaseDatabase.getInstance().getReference().child("Follow").child(profileid)
                            .child("followers").child(firebaseUser.getUid()).removeValue();
                }
            }
        });


        llfolo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FollowersActivity.class);
                intent.putExtra("id",profileid);
                intent.putExtra("title","followers");
                startActivity(intent);
            }
        });

        llfoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FollowersActivity.class);
                intent.putExtra("id",profileid);
                intent.putExtra("title","following");
                startActivity(intent);
            }
        });



        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                recyclerView.setVisibility(View.VISIBLE);
                recyclerView_anonymous.setVisibility(View.GONE);
                recyclerView_shared.setVisibility(View.GONE);
                progressBar.setVisibility(View.GONE);


            }
        });

        anonymous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.GONE);
                recyclerView_shared.setVisibility(View.GONE);

                recyclerView_anonymous.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);


            }
        });

        shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView.setVisibility(View.GONE);
                recyclerView_anonymous.setVisibility(View.GONE);
                recyclerView_shared.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);



            }
        });


        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        reference.removeEventListener(mDblistener);
    }

    private void addNotifications()
    {
        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        saveCurrentTime = currentTime.format(calFordTime.getTime());

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Notifications").child(profileid);
        HashMap<String,Object> hashMap = new HashMap<>();

        hashMap.put("userid",firebaseUser.getUid());
        hashMap.put("text","started following you");
        hashMap.put("postid","");
        hashMap.put("ispost",false);
        hashMap.put("isapost",false);
        hashMap.put("time", saveCurrentTime);


        reference.push().setValue(hashMap);
    }


    private void userInfo()
    {
        reference = FirebaseDatabase.getInstance().getReference("Users").child(profileid);
        mDblistener = reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (getContext() == null)
                {
                    return;
                }
                User user = dataSnapshot.getValue(User.class);

                Glide.with(getContext()).load(user.getImageurl()).apply(new RequestOptions()
                        .placeholder(R.drawable.circle_profile_holder)
                        .error(R.drawable.frokenpng)).into(image_profile);


                if (user.getBio().equals(""))
                {
                    mid_bar.setVisibility(View.GONE);
                    bio.setVisibility(View.GONE);
                    fullname.setVisibility(View.GONE);
                }

                else
                {
                    bio.setText(user.getBio().toString());
                    fullname.setText("About " + user.getFullname());
                }

                name.setText(user.getFullname());

                if (user.getCountry().equals(""))
                {
                    country.setVisibility(View.GONE);
                    location.setVisibility(View.GONE);
                }

                else
                {
                    country.setText(user.getCountry());

                }

                Glide.with(getContext()).load(user.getCoverurl()).into(cover);
                progressBar.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkFollow()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(firebaseUser.getUid()).child("following");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(profileid).exists())
                {
                    edit_profile.setText("following");

                }
                else {
                    edit_profile.setText("follow");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void getFollowers()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(profileid).child("followers");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                followers.setText("" + dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference()
                .child("Follow").child(profileid).child("following");

        reference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                following.setText("" + dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getStalks() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference()
                .child("Visited").child(profileid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                stalk.setText("" + dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


        private void getNrPosts()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               int i =0;

               for (DataSnapshot snapshot : dataSnapshot.getChildren())
               {
                   Post post = snapshot.getValue(Post.class);
                   if(post.getPublisher().equals(profileid))
                   {
                       i++;
                   }

                   posts.setText(""+i);
               }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }




    private void myFotos()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Post post = snapshot.getValue(Post.class);
                    if (post.getPublisher().equals(profileid))
                    {
                        postList.add(post);
                    }
                }

                Collections.reverse(postList);
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void myAnonymous()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Anonymous Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                apostList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    AnonymousPost post = snapshot.getValue(AnonymousPost.class);
                    if (post.getPublisher().equals(profileid))
                    {
                        apostList.add(post);
                    }
                }

                Collections.reverse(apostList);
                apostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void myShared()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Shared Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                shareList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    SharePost post = snapshot.getValue(SharePost.class);
                    if (post.getPublisher().equals(profileid))
                    {
                        shareList.add(post);
                    }
                }

                Collections.reverse(shareList);
                shareAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }



}
