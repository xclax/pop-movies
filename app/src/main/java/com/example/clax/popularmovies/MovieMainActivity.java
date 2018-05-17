package com.example.clax.popularmovies;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clax.popularmovies.adapter.MovieAdapter;
import com.example.clax.popularmovies.models.Movie;
import com.example.clax.popularmovies.service.AsyncTaskDelegate;
import com.example.clax.popularmovies.service.MovieServiceTask;
import com.example.clax.popularmovies.utils.NetworkUtils;

import java.net.URL;
import java.util.List;

public class MovieMainActivity extends AppCompatActivity implements AsyncTaskDelegate{

    private GridView movieGrid;
    private ProgressBar loadingProgressBar;
    private TextView errorTextView;
    private static final String API_KEY = BuildConfig.API_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_main);
        findViews();

        movieGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = (Movie) parent.getItemAtPosition(position);
                Intent movieDetailsIntent = new Intent(MovieMainActivity.this, MovieDetailsActivity.class);
                movieDetailsIntent.putExtra(Movie.MOVIE_KEY, movie);
                startActivity(movieDetailsIntent);
            }
        });

        URL movieDatabaseURL = NetworkUtils.buildUrl(API_KEY, false);

        checkNetworkConnection(movieDatabaseURL);
    }

    private void checkNetworkConnection(URL movieDatabaseURL) {
        if(NetworkUtils.hasNetwork(this)) {
            showProgressBar();
            new MovieServiceTask( this).execute(movieDatabaseURL);
        } else {
            Toast.makeText(this, getResources().getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
        }
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
            checkNetworkConnection(movieDatabaseURL);
        }

        if(item.getItemId() == R.id.action_popular_movies) {
            movieDatabaseURL = NetworkUtils.buildUrl(API_KEY, false);
           checkNetworkConnection(movieDatabaseURL);
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

    @Override
    public void processFinish(Object output) {
        if(output != null) {
            loadingProgressBar.setVisibility(View.INVISIBLE);
            List<Movie> movies = (List<Movie>) output;
            MovieAdapter movieAdapter = new MovieAdapter(MovieMainActivity.this, movies);
            movieGrid.setAdapter(movieAdapter);

        } else {
            showErrorMessage();
        }
    }

}
