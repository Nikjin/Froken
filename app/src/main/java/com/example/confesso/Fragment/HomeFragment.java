package com.example.confesso.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.confesso.Adapter.PagerViewAdapter;
import com.example.confesso.AnonymousPostActivity;
import com.example.confesso.PostActivity;
import com.example.confesso.R;


public class HomeFragment extends Fragment {


   // ProgressBar progressBar;

    TextView known,anonymous,shared;

    Button butedu,butfashion,butfood,butfiction,butfun,butmusic,butmovie,butphoto,butsci,butstories,butsports,buttravel,butquote,butother;
    ImageView nofilter;

    private ViewPager mainPager;
    private PagerViewAdapter viewAdapter;

   // private List<String> followingList;


    private ImageView butaddp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view =inflater.inflate(R.layout.fragment_home,container,false);


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

        //progressBar =view.findViewById(R.id.progress_circular);
        mainPager =view.findViewById(R.id.mainPager);


       // final HanonymousFragment fragment = (HanonymousFragment) getChildFragmentManager().getFragments().get(1);


        mainPager.setOffscreenPageLimit(3);




        butaddp=view.findViewById(R.id.butaddp);
        butaddp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getActivity(),butaddp);
                popupMenu.getMenuInflater().inflate(R.menu.popupmenu,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(getActivity(),""+item.getTitle(), Toast.LENGTH_SHORT).show();

                        if(item.getItemId()==R.id.post){

                            startActivity(new Intent(getActivity(), PostActivity.class));
                        }

                       else if(item.getItemId()==R.id.anonymouspost){

                            startActivity(new Intent(getActivity(), AnonymousPostActivity.class));
                        }


                        return true;

                    }

                });

                popupMenu.show();
            }
        });


     /*  nofilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fragment.butedu.setBackgroundResource(R.drawable.custom_button4);
                fragment.butedu.setTextColor(Color.parseColor("#443A9E"));

                fragment.butfashion.setBackgroundResource(R.drawable.custom_button4);
                fragment.butfashion.setTextColor(Color.parseColor("#443A9E"));

                fragment.butfood.setBackgroundResource(R.drawable.custom_button4);
                fragment.butfood.setTextColor(Color.parseColor("#443A9E"));

                fragment.butfiction.setBackgroundResource(R.drawable.custom_button4);
                fragment.butfiction.setTextColor(Color.parseColor("#443A9E"));

                fragment.butfun.setBackgroundResource(R.drawable.custom_button4);
                fragment.butfun.setTextColor(Color.parseColor("#443A9E"));

                fragment. butmusic.setBackgroundResource(R.drawable.custom_button4);
                fragment.butmusic.setTextColor(Color.parseColor("#443A9E"));

                fragment.butmovie.setBackgroundResource(R.drawable.custom_button4);
                fragment.butmovie.setTextColor(Color.parseColor("#443A9E"));

                fragment.butphoto.setBackgroundResource(R.drawable.custom_button4);
                fragment.butphoto.setTextColor(Color.parseColor("#443A9E"));

                fragment.butsci.setBackgroundResource(R.drawable.custom_button4);
                fragment.butsci.setTextColor(Color.parseColor("#443A9E"));

                fragment.butstories.setBackgroundResource(R.drawable.custom_button4);
                fragment.butstories.setTextColor(Color.parseColor("#443A9E"));

                fragment.butsports.setBackgroundResource(R.drawable.custom_button4);
                fragment.butsports.setTextColor(Color.parseColor("#443A9E"));

                fragment.buttravel.setBackgroundResource(R.drawable.custom_button4);
                fragment. buttravel.setTextColor(Color.parseColor("#443A9E"));

                fragment.butquote.setBackgroundResource(R.drawable.custom_button4);
                fragment.butquote.setTextColor(Color.parseColor("#443A9E"));

                fragment.butother.setBackgroundResource(R.drawable.custom_button4);
                fragment.butother.setTextColor(Color.parseColor("#443A9E"));


                postAdapter.nofilter();
                apostAdapter.nofilter();
                flag = 0;

                progressBar.setVisibility(View.GONE);



            }
        });


        butedu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                butedu.setBackgroundResource(R.drawable.custom_catbut);
                butedu.setTextColor(Color.parseColor("#ffffff"));

                butfashion.setBackgroundResource(R.drawable.custom_button4);
                butfashion.setTextColor(Color.parseColor("#443A9E"));

                butfood.setBackgroundResource(R.drawable.custom_button4);
                butfood.setTextColor(Color.parseColor("#443A9E"));

                butfiction.setBackgroundResource(R.drawable.custom_button4);
                butfiction.setTextColor(Color.parseColor("#443A9E"));

                butfun.setBackgroundResource(R.drawable.custom_button4);
                butfun.setTextColor(Color.parseColor("#443A9E"));

                butmusic.setBackgroundResource(R.drawable.custom_button4);
                butmusic.setTextColor(Color.parseColor("#443A9E"));

                butmovie.setBackgroundResource(R.drawable.custom_button4);
                butmovie.setTextColor(Color.parseColor("#443A9E"));

                butphoto.setBackgroundResource(R.drawable.custom_button4);
                butphoto.setTextColor(Color.parseColor("#443A9E"));

                butsci.setBackgroundResource(R.drawable.custom_button4);
                butsci.setTextColor(Color.parseColor("#443A9E"));

                butstories.setBackgroundResource(R.drawable.custom_button4);
                butstories.setTextColor(Color.parseColor("#443A9E"));

                butsports.setBackgroundResource(R.drawable.custom_button4);
                butsports.setTextColor(Color.parseColor("#443A9E"));

                buttravel.setBackgroundResource(R.drawable.custom_button4);
                buttravel.setTextColor(Color.parseColor("#443A9E"));

                butquote.setBackgroundResource(R.drawable.custom_button4);
                butquote.setTextColor(Color.parseColor("#443A9E"));

                butother.setBackgroundResource(R.drawable.custom_button4);
                butother.setTextColor(Color.parseColor("#443A9E"));

                postAdapter.filteredu();
                apostAdapter.filteredu();


                flag = 1;


            }
        });

        butfashion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                butedu.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butfashion.setBackgroundResource(R.drawable.custom_catbut);
                butfashion.setTextColor(Color.parseColor("#FFFFFF"));

                butfood.setBackgroundResource(R.drawable.custom_button4);
                butfood.setTextColor(Color.parseColor("#443A9E"));

                butfiction.setBackgroundResource(R.drawable.custom_button4);
                butfiction.setTextColor(Color.parseColor("#443A9E"));

                butfun.setBackgroundResource(R.drawable.custom_button4);
                butfun.setTextColor(Color.parseColor("#443A9E"));

                butmusic.setBackgroundResource(R.drawable.custom_button4);
                butmusic.setTextColor(Color.parseColor("#443A9E"));

                butmovie.setBackgroundResource(R.drawable.custom_button4);
                butmovie.setTextColor(Color.parseColor("#443A9E"));

                butphoto.setBackgroundResource(R.drawable.custom_button4);
                butphoto.setTextColor(Color.parseColor("#443A9E"));

                butsci.setBackgroundResource(R.drawable.custom_button4);
                butsci.setTextColor(Color.parseColor("#443A9E"));

                butstories.setBackgroundResource(R.drawable.custom_button4);
                butstories.setTextColor(Color.parseColor("#443A9E"));

                butsports.setBackgroundResource(R.drawable.custom_button4);
                butsports.setTextColor(Color.parseColor("#443A9E"));

                buttravel.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butquote.setBackgroundResource(R.drawable.custom_button4);
                butquote.setTextColor(Color.parseColor("#443A9E"));

                butother.setBackgroundResource(R.drawable.custom_button4);
                butother.setTextColor(Color.parseColor("#443A9E"));

                postAdapter.filterfashion();
                apostAdapter.filterfashion();
                flag = 2;


            }
        });


        butfood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                butedu.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butfashion.setBackgroundResource(R.drawable.custom_button4);
                butfashion.setTextColor(Color.parseColor("#443A9E"));

                butfood.setBackgroundResource(R.drawable.custom_catbut);
                butfood.setTextColor(Color.parseColor("#FFFFFF"));

                butfiction.setBackgroundResource(R.drawable.custom_button4);
                butfiction.setTextColor(Color.parseColor("#443A9E"));

                butfun.setBackgroundResource(R.drawable.custom_button4);
                butfun.setTextColor(Color.parseColor("#443A9E"));

                butmusic.setBackgroundResource(R.drawable.custom_button4);
                butmusic.setTextColor(Color.parseColor("#443A9E"));

                butmovie.setBackgroundResource(R.drawable.custom_button4);
                butmovie.setTextColor(Color.parseColor("#443A9E"));

                butphoto.setBackgroundResource(R.drawable.custom_button4);
                butphoto.setTextColor(Color.parseColor("#443A9E"));

                butsci.setBackgroundResource(R.drawable.custom_button4);
                butsci.setTextColor(Color.parseColor("#443A9E"));

                butstories.setBackgroundResource(R.drawable.custom_button4);
                butstories.setTextColor(Color.parseColor("#443A9E"));

                butsports.setBackgroundResource(R.drawable.custom_button4);
                butsports.setTextColor(Color.parseColor("#443A9E"));

                buttravel.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butquote.setBackgroundResource(R.drawable.custom_button4);
                butquote.setTextColor(Color.parseColor("#443A9E"));

                butother.setBackgroundResource(R.drawable.custom_button4);
                butother.setTextColor(Color.parseColor("#443A9E"));

                postAdapter.filterfood();
                apostAdapter.filterfood();
                flag = 3;


            }
        });

        butfiction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                butedu.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butfashion.setBackgroundResource(R.drawable.custom_button4);
                butfashion.setTextColor(Color.parseColor("#443A9E"));

                butfood.setBackgroundResource(R.drawable.custom_button4);
                butfood.setTextColor(Color.parseColor("#443A9E"));

                butfiction.setBackgroundResource(R.drawable.custom_catbut);
                butfiction.setTextColor(Color.parseColor("#FFFFFF"));

                butfun.setBackgroundResource(R.drawable.custom_button4);
                butfun.setTextColor(Color.parseColor("#443A9E"));

                butmusic.setBackgroundResource(R.drawable.custom_button4);
                butmusic.setTextColor(Color.parseColor("#443A9E"));

                butmovie.setBackgroundResource(R.drawable.custom_button4);
                butmovie.setTextColor(Color.parseColor("#443A9E"));

                butphoto.setBackgroundResource(R.drawable.custom_button4);
                butphoto.setTextColor(Color.parseColor("#443A9E"));

                butsci.setBackgroundResource(R.drawable.custom_button4);
                butsci.setTextColor(Color.parseColor("#443A9E"));

                butstories.setBackgroundResource(R.drawable.custom_button4);
                butstories.setTextColor(Color.parseColor("#443A9E"));

                butsports.setBackgroundResource(R.drawable.custom_button4);
                butsports.setTextColor(Color.parseColor("#443A9E"));

                buttravel.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butquote.setBackgroundResource(R.drawable.custom_button4);
                butquote.setTextColor(Color.parseColor("#443A9E"));

                butother.setBackgroundResource(R.drawable.custom_button4);
                butother.setTextColor(Color.parseColor("#443A9E"));

                postAdapter.filterfiction();
                flag = 4;

                apostAdapter.filterfiction();

            }
        });

        butfun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                butedu.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butfashion.setBackgroundResource(R.drawable.custom_button4);
                butfashion.setTextColor(Color.parseColor("#443A9E"));

                butfood.setBackgroundResource(R.drawable.custom_button4);
                butfood.setTextColor(Color.parseColor("#443A9E"));

                butfiction.setBackgroundResource(R.drawable.custom_button4);
                butfiction.setTextColor(Color.parseColor("#443A9E"));

                butfun.setBackgroundResource(R.drawable.custom_catbut);
                butfun.setTextColor(Color.parseColor("#FFFFFF"));

                butmusic.setBackgroundResource(R.drawable.custom_button4);
                butmusic.setTextColor(Color.parseColor("#443A9E"));

                butmovie.setBackgroundResource(R.drawable.custom_button4);
                butmovie.setTextColor(Color.parseColor("#443A9E"));

                butphoto.setBackgroundResource(R.drawable.custom_button4);
                butphoto.setTextColor(Color.parseColor("#443A9E"));

                butsci.setBackgroundResource(R.drawable.custom_button4);
                butsci.setTextColor(Color.parseColor("#443A9E"));

                butstories.setBackgroundResource(R.drawable.custom_button4);
                butstories.setTextColor(Color.parseColor("#443A9E"));

                butsports.setBackgroundResource(R.drawable.custom_button4);
                butsports.setTextColor(Color.parseColor("#443A9E"));

                buttravel.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butquote.setBackgroundResource(R.drawable.custom_button4);
                butquote.setTextColor(Color.parseColor("#443A9E"));

                butother.setBackgroundResource(R.drawable.custom_button4);
                butother.setTextColor(Color.parseColor("#443A9E"));

                postAdapter.filterfun();
                apostAdapter.filterfun();

                flag = 5;


            }
        });

        butmusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                butedu.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butfashion.setBackgroundResource(R.drawable.custom_button4);
                butfashion.setTextColor(Color.parseColor("#443A9E"));

                butfood.setBackgroundResource(R.drawable.custom_button4);
                butfood.setTextColor(Color.parseColor("#443A9E"));

                butfiction.setBackgroundResource(R.drawable.custom_button4);
                butfiction.setTextColor(Color.parseColor("#443A9E"));

                butfun.setBackgroundResource(R.drawable.custom_button4);
                butfun.setTextColor(Color.parseColor("#443A9E"));

                butmusic.setBackgroundResource(R.drawable.custom_catbut);
                butmusic.setTextColor(Color.parseColor("#FFFFFF"));


                butmovie.setBackgroundResource(R.drawable.custom_button4);
                butmovie.setTextColor(Color.parseColor("#443A9E"));

                butphoto.setBackgroundResource(R.drawable.custom_button4);
                butphoto.setTextColor(Color.parseColor("#443A9E"));

                butsci.setBackgroundResource(R.drawable.custom_button4);
                butsci.setTextColor(Color.parseColor("#443A9E"));

                butstories.setBackgroundResource(R.drawable.custom_button4);
                butstories.setTextColor(Color.parseColor("#443A9E"));

                butsports.setBackgroundResource(R.drawable.custom_button4);
                butsports.setTextColor(Color.parseColor("#443A9E"));

                buttravel.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butquote.setBackgroundResource(R.drawable.custom_button4);
                butquote.setTextColor(Color.parseColor("#443A9E"));

                butother.setBackgroundResource(R.drawable.custom_button4);
                butother.setTextColor(Color.parseColor("#443A9E"));

                postAdapter.filtermusic();
                apostAdapter.filtermusic();

                flag = 6;

            }
        });

        butmovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                butedu.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butfashion.setBackgroundResource(R.drawable.custom_button4);
                butfashion.setTextColor(Color.parseColor("#443A9E"));

                butfood.setBackgroundResource(R.drawable.custom_button4);
                butfood.setTextColor(Color.parseColor("#443A9E"));

                butfiction.setBackgroundResource(R.drawable.custom_button4);
                butfiction.setTextColor(Color.parseColor("#443A9E"));

                butfun.setBackgroundResource(R.drawable.custom_button4);
                butfun.setTextColor(Color.parseColor("#443A9E"));

                butmusic.setBackgroundResource(R.drawable.custom_button4);
                butmusic.setTextColor(Color.parseColor("#443A9E"));

                butmovie.setBackgroundResource(R.drawable.custom_catbut);
                butmovie.setTextColor(Color.parseColor("#FFFFFF"));

                butphoto.setBackgroundResource(R.drawable.custom_button4);
                butphoto.setTextColor(Color.parseColor("#443A9E"));

                butsci.setBackgroundResource(R.drawable.custom_button4);
                butsci.setTextColor(Color.parseColor("#443A9E"));

                butstories.setBackgroundResource(R.drawable.custom_button4);
                butstories.setTextColor(Color.parseColor("#443A9E"));

                butsports.setBackgroundResource(R.drawable.custom_button4);
                butsports.setTextColor(Color.parseColor("#443A9E"));

                buttravel.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butquote.setBackgroundResource(R.drawable.custom_button4);
                butquote.setTextColor(Color.parseColor("#443A9E"));

                butother.setBackgroundResource(R.drawable.custom_button4);
                butother.setTextColor(Color.parseColor("#443A9E"));

                postAdapter.filtermovie();
                apostAdapter.filtermovie();
                flag = 7;


            }
        });

        butphoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                butedu.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butfashion.setBackgroundResource(R.drawable.custom_button4);
                butfashion.setTextColor(Color.parseColor("#443A9E"));

                butfood.setBackgroundResource(R.drawable.custom_button4);
                butfood.setTextColor(Color.parseColor("#443A9E"));

                butfiction.setBackgroundResource(R.drawable.custom_button4);
                butfiction.setTextColor(Color.parseColor("#443A9E"));

                butfun.setBackgroundResource(R.drawable.custom_button4);
                butfun.setTextColor(Color.parseColor("#443A9E"));

                butmusic.setBackgroundResource(R.drawable.custom_button4);
                butmusic.setTextColor(Color.parseColor("#443A9E"));

                butmovie.setBackgroundResource(R.drawable.custom_button4);
                butmovie.setTextColor(Color.parseColor("#443A9E"));

                butphoto.setBackgroundResource(R.drawable.custom_catbut);
                butphoto.setTextColor(Color.parseColor("#FFFFFF"));

                butsci.setBackgroundResource(R.drawable.custom_button4);
                butsci.setTextColor(Color.parseColor("#443A9E"));

                butstories.setBackgroundResource(R.drawable.custom_button4);
                butstories.setTextColor(Color.parseColor("#443A9E"));

                butsports.setBackgroundResource(R.drawable.custom_button4);
                butsports.setTextColor(Color.parseColor("#443A9E"));

                buttravel.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butquote.setBackgroundResource(R.drawable.custom_button4);
                butquote.setTextColor(Color.parseColor("#443A9E"));

                butother.setBackgroundResource(R.drawable.custom_button4);
                butother.setTextColor(Color.parseColor("#443A9E"));

                postAdapter.filterphoto();
                apostAdapter.filterphoto();
                flag = 8;


            }
        });

        butsci.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                butedu.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butfashion.setBackgroundResource(R.drawable.custom_button4);
                butfashion.setTextColor(Color.parseColor("#443A9E"));

                butfood.setBackgroundResource(R.drawable.custom_button4);
                butfood.setTextColor(Color.parseColor("#443A9E"));

                butfiction.setBackgroundResource(R.drawable.custom_button4);
                butfiction.setTextColor(Color.parseColor("#443A9E"));

                butfun.setBackgroundResource(R.drawable.custom_button4);
                butfun.setTextColor(Color.parseColor("#443A9E"));

                butmusic.setBackgroundResource(R.drawable.custom_button4);
                butmusic.setTextColor(Color.parseColor("#443A9E"));

                butmovie.setBackgroundResource(R.drawable.custom_button4);
                butmovie.setTextColor(Color.parseColor("#443A9E"));

                butphoto.setBackgroundResource(R.drawable.custom_button4);
                butphoto.setTextColor(Color.parseColor("#443A9E"));

                butsci.setBackgroundResource(R.drawable.custom_catbut);
                butsci.setTextColor(Color.parseColor("#FFFFFF"));


                butstories.setBackgroundResource(R.drawable.custom_button4);
                butstories.setTextColor(Color.parseColor("#443A9E"));

                butsports.setBackgroundResource(R.drawable.custom_button4);
                butsports.setTextColor(Color.parseColor("#443A9E"));

                buttravel.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butquote.setBackgroundResource(R.drawable.custom_button4);
                butquote.setTextColor(Color.parseColor("#443A9E"));

                butother.setBackgroundResource(R.drawable.custom_button4);
                butother.setTextColor(Color.parseColor("#443A9E"));

                postAdapter.filtersci();
                apostAdapter.filtersci();
                flag = 9;


            }
        });

        butstories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                butedu.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butfashion.setBackgroundResource(R.drawable.custom_button4);
                butfashion.setTextColor(Color.parseColor("#443A9E"));

                butfood.setBackgroundResource(R.drawable.custom_button4);
                butfood.setTextColor(Color.parseColor("#443A9E"));

                butfiction.setBackgroundResource(R.drawable.custom_button4);
                butfiction.setTextColor(Color.parseColor("#443A9E"));

                butfun.setBackgroundResource(R.drawable.custom_button4);
                butfun.setTextColor(Color.parseColor("#443A9E"));

                butmusic.setBackgroundResource(R.drawable.custom_button4);
                butmusic.setTextColor(Color.parseColor("#443A9E"));

                butmovie.setBackgroundResource(R.drawable.custom_button4);
                butmovie.setTextColor(Color.parseColor("#443A9E"));

                butphoto.setBackgroundResource(R.drawable.custom_button4);
                butphoto.setTextColor(Color.parseColor("#443A9E"));

                butsci.setBackgroundResource(R.drawable.custom_button4);
                butsci.setTextColor(Color.parseColor("#443A9E"));

                butstories.setBackgroundResource(R.drawable.custom_catbut);
                butstories.setTextColor(Color.parseColor("#FFFFFF"));

                butsports.setBackgroundResource(R.drawable.custom_button4);
                butsports.setTextColor(Color.parseColor("#443A9E"));

                buttravel.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butquote.setBackgroundResource(R.drawable.custom_button4);
                butquote.setTextColor(Color.parseColor("#443A9E"));

                butother.setBackgroundResource(R.drawable.custom_button4);
                butother.setTextColor(Color.parseColor("#443A9E"));

                postAdapter.filterstories();
                apostAdapter.filterstories();

                flag = 10;


            }
        });

        butsports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                butedu.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butfashion.setBackgroundResource(R.drawable.custom_button4);
                butfashion.setTextColor(Color.parseColor("#443A9E"));

                butfood.setBackgroundResource(R.drawable.custom_button4);
                butfood.setTextColor(Color.parseColor("#443A9E"));

                butfiction.setBackgroundResource(R.drawable.custom_button4);
                butfiction.setTextColor(Color.parseColor("#443A9E"));

                butfun.setBackgroundResource(R.drawable.custom_button4);
                butfun.setTextColor(Color.parseColor("#443A9E"));

                butmusic.setBackgroundResource(R.drawable.custom_button4);
                butmusic.setTextColor(Color.parseColor("#443A9E"));

                butmovie.setBackgroundResource(R.drawable.custom_button4);
                butmovie.setTextColor(Color.parseColor("#443A9E"));

                butphoto.setBackgroundResource(R.drawable.custom_button4);
                butphoto.setTextColor(Color.parseColor("#443A9E"));

                butsci.setBackgroundResource(R.drawable.custom_button4);
                butsci.setTextColor(Color.parseColor("#443A9E"));

                butstories.setBackgroundResource(R.drawable.custom_button4);
                butstories.setTextColor(Color.parseColor("#443A9E"));

                butsports.setBackgroundResource(R.drawable.custom_catbut);
                butsports.setTextColor(Color.parseColor("#FFFFFF"));

                buttravel.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butquote.setBackgroundResource(R.drawable.custom_button4);
                butquote.setTextColor(Color.parseColor("#443A9E"));

                butother.setBackgroundResource(R.drawable.custom_button4);
                butother.setTextColor(Color.parseColor("#443A9E"));

                postAdapter.filtersports();
                apostAdapter.filtersports();

                flag = 11;


            }
        });
        buttravel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                butedu.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butfashion.setBackgroundResource(R.drawable.custom_button4);
                butfashion.setTextColor(Color.parseColor("#443A9E"));

                butfood.setBackgroundResource(R.drawable.custom_button4);
                butfood.setTextColor(Color.parseColor("#443A9E"));

                butfiction.setBackgroundResource(R.drawable.custom_button4);
                butfiction.setTextColor(Color.parseColor("#443A9E"));

                butfun.setBackgroundResource(R.drawable.custom_button4);
                butfun.setTextColor(Color.parseColor("#443A9E"));

                butmusic.setBackgroundResource(R.drawable.custom_button4);
                butmusic.setTextColor(Color.parseColor("#443A9E"));

                butmovie.setBackgroundResource(R.drawable.custom_button4);
                butmovie.setTextColor(Color.parseColor("#443A9E"));

                butphoto.setBackgroundResource(R.drawable.custom_button4);
                butphoto.setTextColor(Color.parseColor("#443A9E"));

                butsci.setBackgroundResource(R.drawable.custom_button4);
                butsci.setTextColor(Color.parseColor("#443A9E"));

                butstories.setBackgroundResource(R.drawable.custom_button4);
                butstories.setTextColor(Color.parseColor("#443A9E"));

                butsports.setBackgroundResource(R.drawable.custom_button4);
                butsports.setTextColor(Color.parseColor("#443A9E"));

                buttravel.setBackgroundResource(R.drawable.custom_catbut);
                buttravel.setTextColor(Color.parseColor("#FFFFFF"));


                butquote.setBackgroundResource(R.drawable.custom_button4);
                butquote.setTextColor(Color.parseColor("#443A9E"));

                butother.setBackgroundResource(R.drawable.custom_button4);
                butother.setTextColor(Color.parseColor("#443A9E"));

                postAdapter.filtertravel();
                apostAdapter.filtertravel();

                flag = 12;


            }
        });
        butquote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                butedu.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butfashion.setBackgroundResource(R.drawable.custom_button4);
                butfashion.setTextColor(Color.parseColor("#443A9E"));

                butfood.setBackgroundResource(R.drawable.custom_button4);
                butfood.setTextColor(Color.parseColor("#443A9E"));

                butfiction.setBackgroundResource(R.drawable.custom_button4);
                butfiction.setTextColor(Color.parseColor("#443A9E"));

                butfun.setBackgroundResource(R.drawable.custom_button4);
                butfun.setTextColor(Color.parseColor("#443A9E"));

                butmusic.setBackgroundResource(R.drawable.custom_button4);
                butmusic.setTextColor(Color.parseColor("#443A9E"));

                butmovie.setBackgroundResource(R.drawable.custom_button4);
                butmovie.setTextColor(Color.parseColor("#443A9E"));

                butphoto.setBackgroundResource(R.drawable.custom_button4);
                butphoto.setTextColor(Color.parseColor("#443A9E"));

                butsci.setBackgroundResource(R.drawable.custom_button4);
                butsci.setTextColor(Color.parseColor("#443A9E"));

                butstories.setBackgroundResource(R.drawable.custom_button4);
                butstories.setTextColor(Color.parseColor("#443A9E"));

                butsports.setBackgroundResource(R.drawable.custom_button4);
                butsports.setTextColor(Color.parseColor("#443A9E"));

                buttravel.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                buttravel.setBackgroundResource(R.drawable.custom_button4);
                buttravel.setTextColor(Color.parseColor("#443A9E"));

                butquote.setBackgroundResource(R.drawable.custom_catbut);
                butquote.setTextColor(Color.parseColor("#ffffff"));

                butother.setBackgroundResource(R.drawable.custom_button4);
                butother.setTextColor(Color.parseColor("#443A9E"));

                postAdapter.filterquote();
                apostAdapter.filterquote();

                flag = 13;


            }
        });
        butother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                butedu.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butfashion.setBackgroundResource(R.drawable.custom_button4);
                butfashion.setTextColor(Color.parseColor("#443A9E"));

                butfood.setBackgroundResource(R.drawable.custom_button4);
                butfood.setTextColor(Color.parseColor("#443A9E"));

                butfiction.setBackgroundResource(R.drawable.custom_button4);
                butfiction.setTextColor(Color.parseColor("#443A9E"));

                butfun.setBackgroundResource(R.drawable.custom_button4);
                butfun.setTextColor(Color.parseColor("#443A9E"));

                butmusic.setBackgroundResource(R.drawable.custom_button4);
                butmusic.setTextColor(Color.parseColor("#443A9E"));

                butmovie.setBackgroundResource(R.drawable.custom_button4);
                butmovie.setTextColor(Color.parseColor("#443A9E"));

                butphoto.setBackgroundResource(R.drawable.custom_button4);
                butphoto.setTextColor(Color.parseColor("#443A9E"));

                butsci.setBackgroundResource(R.drawable.custom_button4);
                butsci.setTextColor(Color.parseColor("#443A9E"));

                butstories.setBackgroundResource(R.drawable.custom_button4);
                butstories.setTextColor(Color.parseColor("#443A9E"));

                butsports.setBackgroundResource(R.drawable.custom_button4);
                butsports.setTextColor(Color.parseColor("#443A9E"));

                buttravel.setBackgroundResource(R.drawable.custom_button4);
                butedu.setTextColor(Color.parseColor("#443A9E"));

                butquote.setBackgroundResource(R.drawable.custom_button4);
                butquote.setTextColor(Color.parseColor("#443A9E"));

                butother.setBackgroundResource(R.drawable.custom_catbut);
                butother.setTextColor(Color.parseColor("#ffffff"));


                postAdapter.filterother();
                apostAdapter.filterother();

                flag = 14;


            }
        });







        recyclerView= view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        postLists= new ArrayList<>();
        postAdapter = new PostAdapter(getContext(),postLists);
        recyclerView.setAdapter(postAdapter);


        recyclerView1= view.findViewById(R.id.recycler_view1);
        recyclerView1.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager1= new LinearLayoutManager(getContext());
        linearLayoutManager1.setReverseLayout(true);
        linearLayoutManager1.setStackFromEnd(true);
        recyclerView1.setLayoutManager(linearLayoutManager1);

        apostLists= new ArrayList<>();
        apostAdapter = new AnonymousPostAdapter(getContext(),apostLists);
        recyclerView1.setAdapter(apostAdapter);




 */

        known = view.findViewById(R.id.known);
        anonymous = view.findViewById(R.id.anonymous);
        shared = view.findViewById(R.id.shared);




        known.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   recyclerView.setVisibility(View.VISIBLE);
               // recyclerView1.setVisibility(View.GONE);
                mainPager.setCurrentItem(0);
                known.setAlpha(1);
                known.setTextSize(22);
                anonymous.setAlpha((float) 0.60);
                anonymous.setTextSize(16);
                shared.setAlpha((float) 0.60);
                shared.setTextSize(16);


            }
        });

        anonymous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // recyclerView.setVisibility(View.GONE);
             //   recyclerView1.setVisibility(View.VISIBLE);
                mainPager.setCurrentItem(1);

                anonymous.setAlpha(1);
                anonymous.setTextSize(22);
                known.setAlpha((float) 0.60);
                known.setTextSize(16);
                shared.setAlpha((float) 0.60);
                shared.setTextSize(16);
            }
        });

        shared.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mainPager.setCurrentItem(2);

                shared.setAlpha(1);
                shared.setTextSize(22);
                known.setAlpha((float) 0.60);
                known.setTextSize(16);
                anonymous.setAlpha((float) 0.60);
                anonymous.setTextSize(16);

            }
        });

       // checkFollowing();

        viewAdapter = new PagerViewAdapter(getChildFragmentManager());
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
            shared.setAlpha((float) 0.60);
            shared.setTextSize(16);


        }

        else  if (position == 1)
        {
            anonymous.setAlpha(1);
            anonymous.setTextSize(22);
            known.setAlpha((float) 0.60);
            known.setTextSize(16);
            shared.setAlpha((float) 0.60);
            shared.setTextSize(16);
        }

        else  if (position == 2)
        {
            shared.setAlpha(1);
            shared.setTextSize(22);
            known.setAlpha((float) 0.60);
            known.setTextSize(16);
            anonymous.setAlpha((float) 0.60);
            anonymous.setTextSize(16);
        }




    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


}
