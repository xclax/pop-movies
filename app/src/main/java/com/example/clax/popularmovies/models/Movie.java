package com.example.clax.popularmovies.models;

import android.os.Parcel;
import android.os.Parcelable;


public class Movie implements Parcelable{

    private String imagePath;
    private String title;
    private String overview;
    private int voteAverage;
    private String releaseDate;
    public static final String MOVIE_KEY = "movie";

    public Movie(){

    }


    private Movie(Parcel in) {
        imagePath = in.readString();
        title = in.readString();
        overview = in.readString();
        voteAverage = in.readInt();
        releaseDate = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(imagePath);
        dest.writeString(title);
        dest.writeString(overview);
        dest.writeInt(voteAverage);
        dest.writeString(releaseDate);
    }
}
