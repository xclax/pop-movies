package com.example.clax.popularmovies.models;

import java.io.Serializable;

public class Movie implements Serializable{

    private String imagePath;
    private String title;
    private String overview;
    private int voteAverage;
    private String releaseDate;


    public String getImagePath() {
        return "http://image.tmdb.org/t/p/w185/" + imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(int voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

}
