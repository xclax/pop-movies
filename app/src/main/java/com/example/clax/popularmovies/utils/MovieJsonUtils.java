package com.example.clax.popularmovies.utils;

import com.example.clax.popularmovies.exceptions.ConnectionException;
import com.example.clax.popularmovies.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MovieJsonUtils {

    public static List<Movie> getMoviesListFromJson(String moviesJsonString)
            throws JSONException, ConnectionException {

        if(moviesJsonString == null) {
            return null;
        }

        JSONObject moviesJson = new JSONObject(moviesJsonString);
        JSONArray movieArray = moviesJson.getJSONArray("results");
        List<Movie> moviesList = new ArrayList<>();

        for(int i = 0; i < movieArray.length(); i++) {
            JSONObject result = movieArray.getJSONObject(i);

            Movie movie = new Movie();
            movie.setImagePath(result.optString("poster_path"));
            movie.setReleaseDate(result.optString("release_date"));
            movie.setTitle(result.optString("title"));
            movie.setVoteAverage(result.optInt("vote_average"));
            movie.setOverview(result.optString("overview"));
            moviesList.add(movie);
        }
        return moviesList;
    }

}
