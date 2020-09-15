package com.example.confesso.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.confesso.Adapter.PostAdapter;
import com.example.confesso.Model.Post;
import com.example.confesso.R;
import com.google.firebase.auth.FirebaseAuth;
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
public class HknownFragment extends Fragment {

    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    private List<Post> postLists;
    private List<String> followingList;

    public HomeFragment parentFrag;
    ProgressBar progressBar;



    public HknownFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_hknown, container, false);

        progressBar =view.findViewById(R.id.progress_circular);



        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        postLists = new ArrayList<>();
        postAdapter = new PostAdapter(getContext(), postLists);
        recyclerView.setAdapter(postAdapter);


        if(getUserVisibleHint()) {

            parentFrag = ((HomeFragment) this.getParentFragment());

           

            parentFrag.nofilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));


                    postAdapter.nofilter();
                    progressBar.setVisibility(View.GONE);



                }
            });


            parentFrag.butedu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag.butedu.setTextColor(Color.parseColor("#ffffff"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filteredu();

                }
            });

            parentFrag. butfashion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#FFFFFF"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filterfashion();

                }
            });


            parentFrag. butfood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butfood.setTextColor(Color.parseColor("#FFFFFF"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filterfood();

                }
            });

            parentFrag. butfiction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#FFFFFF"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filterfiction();

                }
            });

            parentFrag. butfun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butfun.setTextColor(Color.parseColor("#FFFFFF"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filterfun();

                }
            });

            parentFrag. butmusic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#FFFFFF"));


                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filtermusic();

                }
            });

            parentFrag. butmovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#FFFFFF"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filtermovie();

                }
            });

            parentFrag. butphoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#FFFFFF"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filterphoto();

                }
            });

            parentFrag. butsci.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butsci.setTextColor(Color.parseColor("#FFFFFF"));


                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filtersci();

                }
            });

            parentFrag. butstories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butstories.setTextColor(Color.parseColor("#FFFFFF"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filterstories();

                }
            });

            parentFrag. butsports.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butsports.setTextColor(Color.parseColor("#FFFFFF"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filtersports();

                }
            });
            parentFrag. buttravel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. buttravel.setTextColor(Color.parseColor("#FFFFFF"));


                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filtertravel();

                }
            });
            parentFrag. butquote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butquote.setTextColor(Color.parseColor("#ffffff"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filterquote();

                }
            });
            parentFrag. butother.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butother.setTextColor(Color.parseColor("#ffffff"));

                    postAdapter.filterother();

                }
            });

            checkFollowing();

        }


        //checkFollowing();

        return view;
    }


// In future  you have to change below method with setmaxlifecycle method

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null && isVisibleToUser) {

            parentFrag = ((HomeFragment) this.getParentFragment());

            parentFrag.nofilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));


                    postAdapter.nofilter();
                    progressBar.setVisibility(View.GONE);



                }
            });


            parentFrag.butedu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag.butedu.setTextColor(Color.parseColor("#ffffff"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filteredu();

                }
            });

            parentFrag. butfashion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#FFFFFF"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filterfashion();

                }
            });


            parentFrag. butfood.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butfood.setTextColor(Color.parseColor("#FFFFFF"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filterfood();

                }
            });

            parentFrag. butfiction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#FFFFFF"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filterfiction();

                }
            });

            parentFrag. butfun.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butfun.setTextColor(Color.parseColor("#FFFFFF"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filterfun();

                }
            });

            parentFrag. butmusic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#FFFFFF"));


                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filtermusic();

                }
            });

            parentFrag. butmovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#FFFFFF"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filtermovie();

                }
            });

            parentFrag. butphoto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#FFFFFF"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filterphoto();

                }
            });

            parentFrag. butsci.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butsci.setTextColor(Color.parseColor("#FFFFFF"));


                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filtersci();

                }
            });

            parentFrag. butstories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butstories.setTextColor(Color.parseColor("#FFFFFF"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filterstories();

                }
            });

            parentFrag. butsports.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butsports.setTextColor(Color.parseColor("#FFFFFF"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filtersports();

                }
            });
            parentFrag. buttravel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. buttravel.setTextColor(Color.parseColor("#FFFFFF"));


                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filtertravel();

                }
            });
            parentFrag. butquote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butquote.setTextColor(Color.parseColor("#ffffff"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butother.setTextColor(Color.parseColor("#272727"));

                    postAdapter.filterquote();

                }
            });
            parentFrag. butother.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentFrag.butedu.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.butedu.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfashion.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfashion.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfood.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfood.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfiction.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfiction.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butfun.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butfun.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmusic.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmusic.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butmovie.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butmovie.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butphoto.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butphoto.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsci.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsci.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butstories.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butstories.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butsports.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butsports.setTextColor(Color.parseColor("#272727"));

                    parentFrag. buttravel.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag.buttravel.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butquote.setBackgroundResource(R.drawable.custom_button4);
                    parentFrag. butquote.setTextColor(Color.parseColor("#272727"));

                    parentFrag. butother.setBackgroundResource(R.drawable.custom_catbut);
                    parentFrag. butother.setTextColor(Color.parseColor("#ffffff"));

                    postAdapter.filterother();

                }
            });


            checkFollowing();

        }else{
            // fragment is no longer visible
        }
    }



    private void checkFollowing() {
        followingList = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Follow")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .child("following");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                followingList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    followingList.add(snapshot.getKey());
                }

                readPosts();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void readPosts() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        //  DatabaseReference areference = FirebaseDatabase.getInstance().getReference("AnonymousPosts");


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                postLists.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Post post = snapshot.getValue(Post.class);
                    for (String id : followingList) {
                        if (post.getPublisher().equals(id)) {
                            postLists.add(post);
                        }
                    }
                }


                postAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getActivity(), "" + databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }


}
