package com.basemnasr.movies.topmovies;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basemnasr.movies.topmovies.Adapters.ReviewsAdapter;
import com.basemnasr.movies.topmovies.Adapters.TrailersAdapter;
import com.basemnasr.movies.topmovies.LocalDatabase.LocalDatabaseAdapter;
import com.basemnasr.movies.topmovies.Models.ReviewsModel;
import com.basemnasr.movies.topmovies.Models.TrailersModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class DetailsFragment extends Fragment {
    ImageView MoviePoster;
    TextView MovieTitle,MovieContent,MovieRelDate,MovieVoting;

    LocalDatabaseAdapter localDatabaseAdapter;
    RecyclerView TrailersRecView;
    RecyclerView ReviewsRecView;
    List<TrailersModel> trailers;
    List<ReviewsModel> reviews;
    int selectedMovieId;
    Context context;
    Button AddToFavorit;
    Bundle args;
    Configuration conf;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        args = getArguments();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setRetainInstance(true);
        View   view = inflater.inflate(R.layout.activity_details, container, false);


        ((AppCompatActivity)getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Movie Details");

        conf = getResources().getConfiguration();

        setHasOptionsMenu(true);
        context = getActivity();

        MainActivity mainActivity = new MainActivity();
        localDatabaseAdapter = new LocalDatabaseAdapter(getActivity());
        Log.e("steps","step1");

        Log.e("steps","step2");


        Log.e("steps","step3");
        if (args==null){
            Log.e("steps","step4");
        }else{
            Log.e("steps","step5");
            MoviePoster = (ImageView) view.findViewById(R.id.PosterID);
            MovieTitle = (TextView) view.findViewById(R.id.MovTitleID);
            MovieContent = (TextView) view.findViewById(R.id.contentID);
            MovieRelDate = (TextView) view.findViewById(R.id.relDateID);
            MovieVoting = (TextView) view.findViewById(R.id.voteAverageId);
            AddToFavorit = (Button) view.findViewById(R.id.AddToFavoritBtn);

            selectedMovieId = getArguments().getInt("movieid");
            TrailersRecView = (RecyclerView) view.findViewById(R.id.Trialersrv);
            GridLayoutManager layoutManager = new GridLayoutManager(context, 1);
            TrailersRecView.setHasFixedSize(true);
            TrailersRecView.setLayoutManager(layoutManager);
            getTrailers(selectedMovieId);

            Log.e("steps", "step5");


            ReviewsRecView = (RecyclerView) view.findViewById(R.id.Reviewsrv);
            GridLayoutManager layoutManager2 = new GridLayoutManager(context, 1);
            ReviewsRecView.setHasFixedSize(true);
            ReviewsRecView.setLayoutManager(layoutManager2);
            getReviews(selectedMovieId);

            Log.e("steps", "step6");



            Picasso.with(getContext()).load(getArguments().getString("PosterPath")).into(MoviePoster);
            MovieTitle.setText(getArguments().getString("title"));
            MovieContent.setText(getArguments().getString("content"));
            if (getArguments().getString("releaseDate").length() > 1) {
                MovieRelDate.setText(getArguments().getString("releaseDate").substring(0, 4));
            }
            MovieVoting.setText(getArguments().getString("VoteRate") + "/10");

            AddToFavorit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    long id = localDatabaseAdapter.insertData(getArguments().getString("title"), getArguments().getString("PosterPath")
                            , getArguments().getString("releaseDate"), getArguments().getString("VoteRate"), getArguments().getString("content")
                            , selectedMovieId);
                    // Toast.makeText(DetailsActivity.this, id+"", Toast.LENGTH_SHORT).show();
                }
            });


        }
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_details, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id==android.R.id.home)
        {
            MainFragment mainFragment = new MainFragment();
            if (conf.smallestScreenWidthDp >= 720) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.Maincontainer, mainFragment).commit();

            }else{
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment).commit();
            }
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Pop Movies");

        }


        return super.onOptionsItemSelected(item);
    }


    private void getTrailers(int movieId) {

        String url = "http://api.themoviedb.org/3/movie/" + movieId + "/videos?api_key=13d6d170dceb2e3e7c202d479192976d";

        StringRequest moviesrequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {

                    Log.e("TrailersResponsing", response);
                    JSONObject object = new JSONObject(response);
                    JSONArray trailersarray = object.getJSONArray("results");

                    trailers = new ArrayList<>();

                    for (int i = 0; i < trailersarray.length(); i++) {
                        JSONObject currentObject = trailersarray.getJSONObject(i);
                        String title = currentObject.getString("name");
                        String movieurlid = currentObject.getString("key");


                        TrailersModel trailer = new TrailersModel(title, movieurlid);
                        trailers.add(trailer);
                    }
                    TrailersAdapter adapter = new TrailersAdapter(trailers, context);
                    TrailersRecView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error + "", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(context).add(moviesrequest);

    }

    private void getReviews(int movieId) {


        String url = "http://api.themoviedb.org/3/movie/" + movieId + "/reviews?api_key=13d6d170dceb2e3e7c202d479192976d";

        StringRequest moviesrequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                try {

                    Log.e("ReviewsResponsing", response);
                    JSONObject object = new JSONObject(response);
                    JSONArray trailersarray = object.getJSONArray("results");

                    reviews = new ArrayList<>();

                    for (int i = 0; i < trailersarray.length(); i++) {
                        JSONObject currentObject = trailersarray.getJSONObject(i);
                        String author = currentObject.getString("author");
                        String content = currentObject.getString("content");

                        ReviewsModel review = new ReviewsModel(author, content);
                        reviews.add(review);
                    }
                    ReviewsAdapter adapter = new ReviewsAdapter(reviews, context);
                    ReviewsRecView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error + "", Toast.LENGTH_SHORT).show();
            }
        });

        Volley.newRequestQueue(context).add(moviesrequest);

    }


}
