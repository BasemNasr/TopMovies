package com.basemnasr.movies.topmovies;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.basemnasr.movies.topmovies.Adapters.MainRecAdapter;
import com.basemnasr.movies.topmovies.LocalDatabase.LocalDatabaseAdapter;

/**
 * Created by BasemNasr on 4/11/2016.
 */
public class FavouritsFragment extends Fragment {

    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    MainRecAdapter adapter;
    ProgressDialog mDialog;
    String url;
    TextView FailedConTV;
    LocalDatabaseAdapter localDatabaseAdapter;
    String popurl, voturl;
    Configuration conf;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Favorits Movies");
        conf = view.getResources().getConfiguration();


        localDatabaseAdapter = new LocalDatabaseAdapter(getActivity());

        Configuration conf = view.getResources().getConfiguration();

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        if (conf.smallestScreenWidthDp >= 600) {
            layoutManager = new GridLayoutManager(view.getContext(), 3);
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MainRecAdapter(localDatabaseAdapter.getAllDataAsList(), getContext());
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_products);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }

        });






        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_fav, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {
            getActivity().finish();
        }

        if (id == R.id.Main) {

            MainFragment mainFragment = new MainFragment();
            if (conf.smallestScreenWidthDp >= 600) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Maincontainer, mainFragment).commit();

            }else{
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment).commit();
            }
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Popularity Movies");


        }


        return super.onOptionsItemSelected(item);
    }


}