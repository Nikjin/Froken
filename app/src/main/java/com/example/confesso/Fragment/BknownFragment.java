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

import com.example.confesso.Adapter.SearchAdapter;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class BknownFragment extends Fragment {

    private List<String> mySaves;
    RecyclerView recyclerView_saves;
    SearchAdapter postAdapter;
    List<Post> postList_saves;

    ProgressBar progressBar;

    private FirebaseUser firebaseUser;


    public BknownFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_bknown, container, false);

        progressBar =view.findViewById(R.id.progress_circular);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();



        recyclerView_saves= view.findViewById(R.id.recycler_view_saves);
        recyclerView_saves.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView_saves.setLayoutManager(linearLayoutManager);
        postList_saves= new ArrayList<>();
        postAdapter = new SearchAdapter(getContext(),postList_saves);
        recyclerView_saves.setAdapter(postAdapter);


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

                readSaves();
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
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
