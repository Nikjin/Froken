package com.example.confesso.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
public class SknownFragment extends Fragment {

    RecyclerView recyclerView;
    SearchAdapter searchAdapter;
    List<Post> mData;

    public SearchFragment parentFrag;

    ProgressBar progressBar;

    FirebaseUser firebaseUser;



    public SknownFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_sknown, container, false);

        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        progressBar =view.findViewById(R.id.progress_circular);



        recyclerView = view.findViewById(R.id.recycler_view);
        mData = new ArrayList<>();
        searchAdapter = new SearchAdapter(getContext(), mData);
        recyclerView.setAdapter(searchAdapter);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);


        if(getUserVisibleHint()) {

            parentFrag = ((SearchFragment) this.getParentFragment());

            parentFrag.searchInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                    searchAdapter.getFilter().filter(s);


                    parentFrag.search = s;


                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


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


                    searchAdapter.nofilter();
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

                    searchAdapter.filteredu();

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

                    searchAdapter.filterfashion();

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

                    searchAdapter.filterfood();

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

                    searchAdapter.filterfiction();

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

                    searchAdapter.filterfun();

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

                    searchAdapter.filtermusic();

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

                    searchAdapter.filtermovie();

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

                    searchAdapter.filterphoto();

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

                    searchAdapter.filtersci();

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

                    searchAdapter.filterstories();

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

                    searchAdapter.filtersports();

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

                    searchAdapter.filtertravel();

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

                    searchAdapter.filterquote();

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

                    searchAdapter.filterother();

                }
            });

            readUsers();

        }


            return view;
    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getView() != null && isVisibleToUser) {

            parentFrag = ((SearchFragment) this.getParentFragment());
            
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


                searchAdapter.nofilter();
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

                searchAdapter.filteredu();

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

                searchAdapter.filterfashion();

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

                searchAdapter.filterfood();

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

                searchAdapter.filterfiction();

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

                searchAdapter.filterfun();

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

                searchAdapter.filtermusic();

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

                searchAdapter.filtermovie();

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

                searchAdapter.filterphoto();

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

                searchAdapter.filtersci();

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

                searchAdapter.filterstories();

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

                searchAdapter.filtersports();

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

                searchAdapter.filtertravel();

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

                searchAdapter.filterquote();

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

                searchAdapter.filterother();

            }
        });

            parentFrag.searchInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                    searchAdapter.getFilter().filter(s);


                    parentFrag.search = s;


                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            readUsers();

        }else{
            // fragment is no longer visible
        }
    }






    private void readUsers()
    {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Posts");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(parentFrag.searchInput.getText().toString().equals(""))
                {
                    mData.clear();
                    for(DataSnapshot snapshot : dataSnapshot.getChildren())
                    {
                        Post post= snapshot.getValue(Post.class);
                        if (!post.getPublisher().equals(firebaseUser.getUid()))
                        {
                            mData.add(post);

                        }
                    }

                    searchAdapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
