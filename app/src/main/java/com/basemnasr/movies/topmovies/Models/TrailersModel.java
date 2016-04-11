package com.basemnasr.movies.topmovies.Models;

/**
 * Created by BasemNasr on 4/11/2016.
 */
public class TrailersModel {
    String TrailerName;
    String TrailerUrlid;

    public String getTrailerName() {
        return TrailerName;
    }

    public void setTrailerName(String trailerName) {
        TrailerName = trailerName;
    }

    public String getTrailerUrlid() {
        return "https://www.youtube.com/watch?v=" + TrailerUrlid;
    }

    public void setTrailerUrlid(String trailerUrlid) {
        TrailerUrlid = trailerUrlid;
    }

    public TrailersModel(String trailerName,String trailerUrlid) {
        this.TrailerName = trailerName;
        TrailerUrlid = trailerUrlid;
    }
}
