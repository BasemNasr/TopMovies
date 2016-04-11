package com.basemnasr.movies.topmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.basemnasr.movies.topmovies.Models.TrailersModel;
import com.basemnasr.movies.topmovies.R;

import java.util.List;

/**
 * Created by BasemNasr on 4/11/2016.
 */
public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {

    int pos;
    Context con;
    List<TrailersModel> trailerList;


    public TrailersAdapter(List<TrailersModel> models,Context context) {

        trailerList = models;
        con = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent,
                                         int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_row, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final TrailersModel trailersModel = trailerList.get(position);
        viewHolder.TrailerName.setText(trailersModel.getTrailerName());

        viewHolder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailersModel.getTrailerUrlid()));
                con.startActivity(browserIntent);

            }
        });




    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView TrailerName;
        public RelativeLayout relativeLayout;

        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            TrailerName = (TextView) itemLayoutView.findViewById(R.id.trailerTitle);
            relativeLayout = (RelativeLayout) itemLayoutView.findViewById(R.id.trailerRowRL);

        }

    }


    @Override
    public int getItemCount() {
        return trailerList.size();
    }
}