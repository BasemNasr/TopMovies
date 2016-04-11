package com.basemnasr.movies.topmovies.LocalDatabase;

/**
 * Created by BasemNasr on 9/14/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.basemnasr.movies.topmovies.Message;
import com.basemnasr.movies.topmovies.Models.MoviesModel;

import java.util.ArrayList;
import java.util.List;


public class LocalDatabaseAdapter extends SQLiteOpenHelper {


    private static final String Database_Name = "Favorits_DBName";
    private static final String TABLE_NAME = "Favorits_TABLE";
    private static final int DB_VERSION = 1;
    private static final String movie_title = "MovieTitle";
    private static final String Poster_path = "PosterPath";
    private static final String Releas_date = "ReleasDate";
    private static final String vote_Average = "VoteAverage";
    private static final String overview = "OverView";
    private static final String movie_id = "MovieId";
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME
            + " (" + movie_title + " VARCHAR(255), " + Poster_path + " VARCHAR(255), " + Releas_date + " VARCHAR(255), "
            + vote_Average + " VARCHAR(255), " + overview + " VARCHAR(255), " + movie_id + " VARCHAR(255));";
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
    private Context context;
    Boolean isFavorit;


    public LocalDatabaseAdapter(Context context) {
        super(context, Database_Name, null, DB_VERSION);
        this.context = context;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DROP_TABLE);

    }

    public long insertData(String m_title, String P_path, String r_Date, String voteAverage,String over_view,int MovieId) {

        SQLiteDatabase db = getWritableDatabase();
        String[] columns = {movie_title, Poster_path, Releas_date,
                vote_Average,overview,movie_id};


         isFavorit = false;
        long id = -1;
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            String p_path = cursor.getString(1);
            if (p_path.equals(P_path)){
                isFavorit = true;
            }
        }

        if (isFavorit == false){
            ContentValues contentValues = new ContentValues();
            contentValues.put(movie_title, m_title);
            contentValues.put(Poster_path, P_path);
            contentValues.put(Releas_date, r_Date);
            contentValues.put(vote_Average, voteAverage);
            contentValues.put(overview, over_view);
            contentValues.put(movie_id, MovieId);

            // id = rowID if successfully inserted if no id =-1
             id = db.insert(TABLE_NAME, null, contentValues);
            Message.message(context,"Movie Added Successfull to Favorits List");
        }else{
            Message.message(context, "This Movie Is In Favorit");
        }

        return id;
    }



    public List<MoviesModel> getAllDataAsList() {
        SQLiteDatabase db = getWritableDatabase();
        List<MoviesModel> movies = new ArrayList<>();

        String[] columns = {movie_title, Poster_path, Releas_date,
                vote_Average,overview,movie_id};

        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            MoviesModel movie = new MoviesModel();
            movie.setTitle(cursor.getString(0));
            movie.setPosterPath(cursor.getString(1));
            movie.setReleaseDate(cursor.getString(2));
            movie.setVoteAverage(cursor.getString(3));
            movie.setOverview(cursor.getString(4));
            movie.setMovieId(cursor.getInt(5));
            movies.add(movie);

        }
        return movies;
    }

}



