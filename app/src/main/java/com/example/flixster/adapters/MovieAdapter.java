package com.example.flixster.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.DetailActivity;
import com.example.flixster.R;
import com.example.flixster.models.Movie;

import org.jetbrains.annotations.NotNull;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/* The MovieAdapter class contains the ViewHolder which holds references to each element in the
RecyclerView, or each movie in the list
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies){
        this.context = context;
        this.movies = movies;
    }

    // will inflate the layout from XML and return layout inside a ViewHolder
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // populates data into the View through the ViewHolder
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);

        // get movies at their respective position
        Movie movie = movies.get(position);

        // bind movie data into the ViewHolders
        holder.bind(movie);
    }

    // returns the total count of the items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    // the ViewHolder is a representation each row (element) in the RecyclerView
    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout container;
        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById((R.id.ivPoster));
            container = itemView.findViewById(R.id.container);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;

            // loading the poster/backdrop path happens in the bind function of data to ViewHolder

            if(context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                imageUrl = movie.getBackdropPath(); // if in landscape
            } else{
                imageUrl = movie.getPosterPath(); // if NOT in landscape, aka if in portrait mode
            }

            // Glide is the library used to render remote images since Android cannot natively do it
            // Glide.with(context).load(imageUrl).into(ivPoster); // default image loading, sharp corners

            int radius = 30; // corner radius, the greater the more rounded
            int margin = 10; // crop margin, 0 for corners with no crop
            Glide.with(context).load(imageUrl).transform(new RoundedCornersTransformation(radius, margin)).into(ivPoster);


            /* Goal 1: Register click listener for whole movie container
            Goal 2: Navigate to a new activity upon tapping on movie container
             */
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Toast.makeText(context, movie.getTitle(), Toast.LENGTH_SHORT).show();
                    // Toast is a small pop up near the bottom of the screen that demonstrates action

                    Intent i = new Intent(context, DetailActivity.class); // target is DetailActivity

                    i.putExtra("movie", Parcels.wrap(movie));

                    context.startActivity(i);
                }
            });
        }
    }
}
