package com.basemnasr.movies.topmovies;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basemnasr.movies.topmovies.Adapters.MainRecAdapter;
import com.basemnasr.movies.topmovies.Models.MoviesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {
    RecyclerView recyclerView;
    MainRecAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressDialog mDialog;
    String url;
    TextView FailedConTV;
    List<MoviesModel> movies;
  public  String popurl, voturl;
    Configuration conf;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        view = inflater.inflate(R.layout.fragment_main, container, false);
        setHasOptionsMenu(true);


        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(false);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        FailedConTV = (TextView) view.findViewById(R.id.failedconId);

        popurl = "http://api.themoviedb.org/3/movie/popular?sort_by=popularity.desc&api_key=13d6d170dceb2e3e7c202d479192976d";
        voturl = "http://api.themoviedb.org/3/movie/top_rated?sort_by=vote_average.desc&api_key=13d6d170dceb2e3e7c202d479192976d";

         conf = view.getResources().getConfiguration();

        recyclerView = (RecyclerView) view.findViewById(R.id.rv);
        GridLayoutManager layoutManager = new GridLayoutManager(view.getContext(), 2);
        if (conf.smallestScreenWidthDp >= 600) {
             layoutManager = new GridLayoutManager(view.getContext(), 3);
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        if(savedInstanceState == null){
            refresh(popurl);
        }

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_products);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh(popurl);
                swipeRefreshLayout.setRefreshing(false);
                ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Pop Movies");

            }

        });


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    private void refresh(String url) {

        mDialog = new ProgressDialog(getContext());
        mDialog.setMessage("Loading . . .");
        mDialog.show();

        StringRequest moviesrequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {
                    FailedConTV.setVisibility(View.GONE);

                    Log.e("MainActPostsResponsing", response);
                    JSONObject object = new JSONObject(response);
                    JSONArray moviesarray = object.getJSONArray("results");

                    movies = new ArrayList<>();

                    for (int i = 0; i < moviesarray.length(); i++) {
                        JSONObject currentObject = moviesarray.getJSONObject(i);
                        String title = currentObject.getString("title");
                        String PosterPath = currentObject.getString("poster_path");
                        String ReleaseDate = currentObject.getString("release_date");
                        String VoteAverage = currentObject.getString("vote_average");
                        String Overview = currentObject.getString("overview");
                        int movieId = currentObject.getInt("id");

                        MoviesModel movie = new MoviesModel(movieId,title, "https://image.tmdb.org/t/p/w185" + PosterPath, ReleaseDate, VoteAverage, Overview);
                        movies.add(movie);
                    }
                    MainRecAdapter adapter = new MainRecAdapter(movies, getContext());
                    recyclerView.setAdapter(adapter);
                    mDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                FailedConTV.setVisibility(View.VISIBLE);

            }
        });

        Volley.newRequestQueue(getContext()).add(moviesrequest);


    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
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

        if (id == R.id.sort_popularity) {
            refresh(popurl);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Pop Movies");

        }

        if (id == R.id.sort_rating) {
            refresh(voturl);
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Voting Movies");

        }
        if (id == R.id.favorit){


            FavouritsFragment favouritsFragment = new FavouritsFragment();
            if (conf.smallestScreenWidthDp >= 720) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Maincontainer, favouritsFragment).commit();
            }else {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, favouritsFragment).commit();
            }
        }

        return super.onOptionsItemSelected(item);
    }


}
