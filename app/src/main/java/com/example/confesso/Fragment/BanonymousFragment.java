package com.example.confesso.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.confesso.Adapter.AnonymousSearchAdapter;
import com.example.confesso.Adapter.SharedPagerViewAdapter;
import com.example.confesso.Model.AnonymousPost;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class BanonymousFragment extends Fragment {

    RecyclerView arecyclerView_saves;
    AnonymousSearchAdapter apostAdapter;
    List<AnonymousPost> apostList_saves;
    private List<String> mySaves;
    private ViewPager mainPager;
    private SharedPagerViewAdapter viewAdapter;

    ProgressBar progressBar;

    private FirebaseUser firebaseUser;

    public BanonymousFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_banonymous, container, false);

        progressBar =view.findViewById(R.id.progress_circular);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        arecyclerView_saves= view.findViewById(R.id.recycler_view_saves1);
        arecyclerView_saves.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1= new LinearLayoutManager(getContext());
        linearLayoutManager1.setReverseLayout(true);
        linearLayoutManager1.setStackFromEnd(true);
        arecyclerView_saves.setLayoutManager(linearLayoutManager1);
        apostList_saves= new ArrayList<>();
        apostAdapter = new AnonymousSearchAdapter(getContext(),apostList_saves);
        arecyclerView_saves.setAdapter(apostAdapter);

        mysaves();
        return view;
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

                areadSaves();
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
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
