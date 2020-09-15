package com.example.confesso.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.confesso.Adapter.AnonymousSearchAdapter;
import com.example.confesso.Adapter.SearchAdapter;
import com.example.confesso.Adapter.SearchPagerViewAdapter;
import com.example.confesso.Model.AnonymousPost;
import com.example.confesso.Model.Post;
import com.example.confesso.R;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;


public class SearchFragment extends Fragment {


    private ViewPager mainPager;
    private SearchPagerViewAdapter viewAdapter;

    RecyclerView NewsRecyclerview;
    SearchAdapter searchAdapter;
    List<Post> mData;

    RecyclerView aNewsRecyclerview;
    AnonymousSearchAdapter asearchAdapter;
    List<AnonymousPost> amData;


    TextView known,anonymous;
    EditText searchInput ;
    CharSequence search="";

    Button butedu,butfashion,butfood,butfiction,butfun,butmusic,butmovie,butphoto,butsci,butstories,butsports,buttravel,butquote,butother;
    ImageView nofilter;

    FirebaseUser firebaseUser;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_search, container, false);


        // ini view

        butedu = view.findViewById(R.id.butedu);
        butfashion = view.findViewById(R.id.butfashion);
        butfood = view.findViewById(R.id.butfood);
        butfiction = view.findViewById(R.id.butfiction);
        butfun = view.findViewById(R.id.butfun);
        butmusic = view.findViewById(R.id.butmusic);
        butmovie = view.findViewById(R.id.butmovie);
        butphoto = view.findViewById(R.id.butphoto);
        butsci = view.findViewById(R.id.butsci);
        butstories = view.findViewById(R.id.butstories);
        butsports = view.findViewById(R.id.butsports);
        buttravel = view.findViewById(R.id.buttravel);
        butquote = view.findViewById(R.id.butquote);
        butother = view.findViewById(R.id.butother);
        nofilter = view.findViewById(R.id.nofilter);



        searchInput = view.findViewById(R.id.search_input);
        known = view.findViewById(R.id.known);
        anonymous = view.findViewById(R.id.anonymous);




        mainPager =view.findViewById(R.id.mainPager);
        mainPager.setOffscreenPageLimit(2);




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

        viewAdapter = new SearchPagerViewAdapter(getChildFragmentManager());
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


}
