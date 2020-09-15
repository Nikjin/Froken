package com.example.confesso.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.confesso.Adapter.AnonymousSearchAdapter;
import com.example.confesso.Adapter.SearchAdapter;
import com.example.confesso.Adapter.SharedPagerViewAdapter;
import com.example.confesso.Model.AnonymousPost;
import com.example.confesso.Model.Post;
import com.example.confesso.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class BookmarkFragment extends Fragment {

    private List<String> mySaves;
    RecyclerView recyclerView_saves;
    SearchAdapter postAdapter;
    List<Post> postList_saves;

    RecyclerView arecyclerView_saves;
    AnonymousSearchAdapter apostAdapter;
    List<AnonymousPost> apostList_saves;

    TextView known,anonymous;

    private ViewPager mainPager;
    private SharedPagerViewAdapter viewAdapter;


    private FirebaseUser firebaseUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_bookmark, container, false);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();



        mainPager =view.findViewById(R.id.mainPager);
        mainPager.setOffscreenPageLimit(2);



        known = view.findViewById(R.id.known);
        anonymous = view.findViewById(R.id.anonymous);




        known.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainPager.setCurrentItem(0);

                known.setAlpha(1);
                known.setTextSize(22);
                anonymous.setAlpha((float) 0.60);
                anonymous.setTextSize(16);
            }
        });

        anonymous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainPager.setCurrentItem(1);

                anonymous.setAlpha(1);
                anonymous.setTextSize(22);
                known.setAlpha((float) 0.60);
                known.setTextSize(16);

            }
        });

        viewAdapter = new SharedPagerViewAdapter(getChildFragmentManager());
        mainPager.setAdapter(viewAdapter);

        mainPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                changeTabs(position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });





        //  mysaves();



        return view;

    }


    private void changeTabs(int position) {

        if (position == 0)
        {
            known.setAlpha(1);
            known.setTextSize(22);
            anonymous.setAlpha((float) 0.60);
            anonymous.setTextSize(16);
        }

        else  if (position == 1)
        {
            anonymous.setAlpha(1);
            anonymous.setTextSize(22);
            known.setAlpha((float) 0.60);
            known.setTextSize(16);

        }
    }



    private void mysaves()
    {
        mySaves = new ArrayList<>();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Saves")
                .child(firebaseUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    mySaves.add(snapshot.getKey());
                }

                readSaves();
                areadSaves();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readSaves()

    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postList_saves.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    Post post = snapshot.getValue(Post.class);

                    for (String id : mySaves)
                    {
                        if (post.getPostid().equals(id))
                        {
                            postList_saves.add(post);
                        }
                    }

                }
                postAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void areadSaves()

    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Anonymous Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                apostList_saves.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    AnonymousPost post = snapshot.getValue(AnonymousPost.class);

                    for (String id : mySaves)
                    {
                        if (post.getPostid().equals(id))
                        {
                            apostList_saves.add(post);
                        }
                    }

                }
                apostAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
