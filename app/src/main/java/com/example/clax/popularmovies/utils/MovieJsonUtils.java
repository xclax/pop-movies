package com.example.clax.popularmovies.utils;

import android.util.Log;

import com.example.clax.popularmovies.ConnectionException;
import com.example.clax.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieJsonUtils {

    private static final String TAG = MovieJsonUtils.class.getSimpleName();

    public static List<Movie> getMoviesListFromJson(String moviesJsonString)
            throws JSONException, ConnectionException {

        if(moviesJsonString == null) {
          Log.e(TAG, "Connection Error");
          throw new ConnectionException("Error fetching movie data. Please try again");
        }

        JSONObject moviesJson = new JSONObject(moviesJsonString);
        JSONArray movieArray = moviesJson.getJSONArray("results");
        List<Movie> moviesList = new ArrayList<>();

        for(int i = 0; i < movieArray.length(); i++) {
            JSONObject result = movieArray.getJSONObject(i);

            Movie movie = new Movie();
            movie.setImagePath(result.getString("poster_path"));
            movie.setReleaseDate(result.getString("release_date"));
            movie.setTitle(result.getString("title"));
            movie.setVoteAverage(result.getInt("vote_average"));
            movie.setOverview(result.getString("overview"));
            moviesList.add(movie);
        }
        return moviesList;
    }

}
