package com.basemnasr.movies.topmovies.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.basemnasr.movies.topmovies.Models.ReviewsModel;
import com.basemnasr.movies.topmovies.R;

import java.util.List;

/**
 * Created by BasemNasr on 4/11/2016.
 */
public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {


    int pos;
    Context con;
    List<ReviewsModel> reviewList;

    public ReviewsAdapter(List<ReviewsModel> models,Context context) {

        reviewList = models;
        con = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent,
                                         int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_row, null);

        ViewHolder viewHolder = new ViewHolder(itemLayoutView);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {

        final ReviewsModel reviewsModel = reviewList.get(position);
        viewHolder.AutherName.setText(reviewsModel.getAuthor());
        viewHolder.ReviewContent.setText(reviewsModel.getContent());


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView AutherName,ReviewContent;

        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            AutherName = (TextView) itemLayoutView.findViewById(R.id.authorNameId);
            ReviewContent = (TextView) itemLayoutView.findViewById(R.id.reviewContentId);

        }

    }


    @Override
    public int getItemCount() {
        return reviewList.size();
    }
}