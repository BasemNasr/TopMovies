package com.basemnasr.movies.topmovies;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.basemnasr.movies.topmovies.Adapters.MainRecAdapter;
import com.basemnasr.movies.topmovies.Models.MoviesModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    RecyclerView recyclerView;
    MainRecAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog mDialog;
    String url;
    TextView FailedConTV;
    List<MoviesModel> movies;
    String popurl, voturl;
    int Nullable = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = (Toolbar) findViewById(R.id.app_bar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Pop Movies");


        Configuration conf = getResources().getConfiguration();
        if (conf.smallestScreenWidthDp >= 720) {

            Fragment fragment = new MainFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.Maincontainer, fragment);
            fragmentTransaction.commit();

            Fragment fragment2 = new DetailsFragment();
            FragmentManager fragmentManager2 = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
            fragmentTransaction.replace(R.id.Detailscontainer, fragment2);
            fragmentTransaction2.commit();

        } else {
            Fragment fragment = new MainFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.commit();
        }


    }


}
