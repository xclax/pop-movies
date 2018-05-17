package com.example.clax.popularmovies;


import com.example.clax.popularmovies.adapter.MovieAdapter;
import com.example.clax.popularmovies.models.Movie;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.clax.popularmovies.utils.MovieJsonUtils;
import com.example.clax.popularmovies.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class MovieMainActivity extends AppCompatActivity {

    private GridView movieGrid;
    private ProgressBar loadingProgressBar;
    private TextView errorTextView;
    private String API_KEY = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_main);
        API_KEY = getResources().getString(R.string.api_key);
        findViews();

        movieGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie) parent.getItemAtPosition(position);
                Intent movieDetailsIntent = new Intent(MovieMainActivity.this, MovieDetailsActivity.class);
                movieDetailsIntent.putExtra("movie", movie);
                startActivity(movieDetailsIntent);
            }
        });

        URL movieDatabaseURL = NetworkUtils.buildUrl(API_KEY, false);
        new GetMoviesTask().execute(movieDatabaseURL);
    }

    private void findViews() {
        loadingProgressBar = findViewById(R.id.pb_loading);
        errorTextView = findViewById(R.id.tv_error_message_display);
        movieGrid = findViewById(R.id.gv_movies_grid);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        URL movieDatabaseURL;

        if(item.getItemId() == R.id.action_top_rated_movies) {
            movieDatabaseURL = NetworkUtils.buildUrl(API_KEY, true);
            new GetMoviesTask().execute(movieDatabaseURL);
        }

        if(item.getItemId() == R.id.action_popular_movies) {
            movieDatabaseURL = NetworkUtils.buildUrl(API_KEY, false);
            new GetMoviesTask().execute(movieDatabaseURL);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies, menu);
        return true;
    }

    private void showProgressBar() {
        loadingProgressBar.setVisibility(View.VISIBLE);
        errorTextView.setVisibility(View.INVISIBLE);
    }

    private void showErrorMessage() {
        loadingProgressBar.setVisibility(View.INVISIBLE);
        errorTextView.setVisibility(View.VISIBLE);
   }

    protected class GetMoviesTask extends AsyncTask<URL, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
          showProgressBar();
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
            loadingProgressBar.setVisibility(View.INVISIBLE);

            if(movies != null) {
                MovieAdapter movieAdapter = new MovieAdapter(MovieMainActivity.this, movies);
                movieGrid.setAdapter(movieAdapter);
            } else {
                showErrorMessage();
            }
        }
    }
}
