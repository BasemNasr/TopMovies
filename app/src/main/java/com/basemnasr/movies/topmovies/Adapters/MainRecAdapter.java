package com.basemnasr.movies.topmovies.Adapters;

/**
 * Created by BasemNasr on 3/16/2016.
 */

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.basemnasr.movies.topmovies.DetailsFragment;
import com.basemnasr.movies.topmovies.Models.MoviesModel;
import com.basemnasr.movies.topmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainRecAdapter extends RecyclerView.Adapter<MainRecAdapter.ViewHolder>  {

    int pos;
    public static Context con;
    public static      List<MoviesModel> movieList;
    public  static Configuration conf;
  public static   int supplyToken;

    public MainRecAdapter(List<MoviesModel> models,Context context) {

        movieList = models;
        con = context;
         conf = context.getResources().getConfiguration();
        supplyToken = 1;




    }


    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent,
                                         int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_rec_row, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final MoviesModel moviesModel = movieList.get(position);
        Picasso.with(con).load(moviesModel.getPosterPath()).into(viewHolder.PosterMovie);


        viewHolder.PosterMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

       

                if (conf.smallestScreenWidthDp >= 600) {

                    Bundle bundle = new Bundle();
                    bundle.putString("PosterPath", moviesModel.getPosterPath());
                    bundle.putString("title", moviesModel.getTitle());
                    bundle.putString("content", moviesModel.getOverview());
                    bundle.putString("releaseDate",moviesModel.getReleaseDate());
                    bundle.putString("VoteRate", moviesModel.getVoteAverage());
                    bundle.putInt("movieid", moviesModel.getMovieId());
                    DetailsFragment detailsFragment = new DetailsFragment();
                    detailsFragment.setArguments(bundle);
                    ((FragmentActivity) con).getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.Detailscontainer, detailsFragment).commit();


                }else {

                    Bundle bundle = new Bundle();
                    bundle.putString("PosterPath", moviesModel.getPosterPath());
                    bundle.putString("title", moviesModel.getTitle());
                    bundle.putString("content", moviesModel.getOverview());
                    bundle.putString("releaseDate",moviesModel.getReleaseDate());
                    bundle.putString("VoteRate", moviesModel.getVoteAverage());
                    bundle.putInt("movieid", moviesModel.getMovieId());
                    DetailsFragment detailsFragment = new DetailsFragment();
                    detailsFragment.setArguments(bundle);
                    ((FragmentActivity) con).getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.container, detailsFragment).commit();



                }



            }
        });




    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView PosterMovie;



        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            PosterMovie = (ImageView) itemLayoutView.findViewById(R.id.posterId);


            final MoviesModel moviesModel = movieList.get(0);
            if (conf.smallestScreenWidthDp >= 600&& supplyToken!=-1) {

                Bundle bundle = new Bundle();
                bundle.putString("PosterPath", moviesModel.getPosterPath());
                bundle.putString("title", moviesModel.getTitle());
                bundle.putString("content", moviesModel.getOverview());
                bundle.putString("releaseDate",moviesModel.getReleaseDate());
                bundle.putString("VoteRate", moviesModel.getVoteAverage());
                bundle.putInt("movieid", moviesModel.getMovieId());
                DetailsFragment detailsFragment = new DetailsFragment();
                detailsFragment.setArguments(bundle);
                ((FragmentActivity) con).getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.Detailscontainer, detailsFragment).commit();
                supplyToken = -1;
            }


        }

    }


    @Override
    public int getItemCount() {
        return movieList.size();
    }
}