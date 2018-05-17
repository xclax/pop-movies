package com.example.clax.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.clax.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    private Movie movie;
    private ImageView movieImageView;
    private TextView movieTitleTextView;
    private TextView movieOverviewTextView;
    private TextView movieDateTextView;
    private TextView movieRateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null) {
            if(getIntent().hasExtra(Movie.MOVIE_KEY)) {
               movie = bundle.getParcelable(Movie.MOVIE_KEY);
            }
        }

        findLayoutViews();
        setMovieValuesToViews();
    }


    private void findLayoutViews() {
        movieImageView = findViewById(R.id.iv_movie_image);
        movieTitleTextView = findViewById(R.id.tv_movie_title);
        movieOverviewTextView = findViewById(R.id.tv_movie_overview);
        movieDateTextView = findViewById(R.id.tv_movie_date);
        movieRateTextView = findViewById(R.id.tv_movie_rate);
    }

    private void setMovieValuesToViews() {
        Picasso.get().load(movie.getImagePath()).centerInside().resize(150,150).into(movieImageView);
        movieTitleTextView.setText(movie.getTitle());
        movieOverviewTextView.setText(movie.getOverview());
        movieRateTextView.setText(String.valueOf(movie.getVoteAverage()));
        movieDateTextView.setText(movie.getReleaseDate());
    }
}
