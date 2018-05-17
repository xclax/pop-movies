package com.example.clax.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.clax.popularmovies.R;
import com.example.clax.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie>{

    public MovieAdapter(@NonNull Context context, List<Movie> moviesList) {
        super(context, 0, moviesList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Movie movie = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_movie, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.movieImage = convertView.findViewById(R.id.iv_movie_image);
            viewHolder.movieImage.setScaleType(ImageView.ScaleType.FIT_XY);
            viewHolder.movieImage.setPadding(1,1,1,1);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(movie != null) {
            Picasso.get().load(movie.getImagePath()).into(viewHolder.movieImage);
        }

        return convertView;
    }

    static class ViewHolder {
        ImageView movieImage;
    }
}
