package com.example.clax.popularmovies.service;

import android.os.AsyncTask;

import com.example.clax.popularmovies.exceptions.ConnectionException;
import com.example.clax.popularmovies.models.Movie;
import com.example.clax.popularmovies.utils.MovieJsonUtils;
import com.example.clax.popularmovies.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MovieServiceTask extends AsyncTask<URL, Void, List<Movie>>{

    private final AsyncTaskDelegate taskDelegate;

    public MovieServiceTask(AsyncTaskDelegate response) {
        this.taskDelegate = response;
    }


    @Override
    protected List<Movie> doInBackground(URL... urls) {
        List<Movie> moviesList;
        String response = null;

        try {
            response = NetworkUtils.getResponseFromHttpUrl(urls[0]);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            moviesList = MovieJsonUtils.getMoviesListFromJson(response);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }catch(ConnectionException e) {
            e.printStackTrace();
            return null;
        }
        return moviesList;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
       super.onPostExecute(movies);
       if(taskDelegate != null) {
           taskDelegate.processFinish(movies);
       }
    }
}
