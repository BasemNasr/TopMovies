package com.basemnasr.movies.topmovies;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by link.net on 13/08/2015.
 */
public class Message {

    public static void message (Context context,String message)
    {
        Toast.makeText(context,message, Toast.LENGTH_LONG).show();
    }
}
