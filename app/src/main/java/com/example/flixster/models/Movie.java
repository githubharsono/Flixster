package com.example.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Movie {

    String posterPath;
    String backdropPath;
    String title;
    String overview;
    double rating;
    int movieID;

    // empty constructor needed by Parcel library
    public Movie(){}

    public Movie(JSONObject jsonObject) throws JSONException { // takes in a JSONObject because we know API is sending JSON objects
        // throws exception instead of try/catch, means calling function must handle the exception
        posterPath = jsonObject.getString("poster_path");
        backdropPath = jsonObject.getString("backdrop_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        rating = jsonObject.getDouble("vote_average");
        movieID = jsonObject.getInt("id");
    }

    // this method is responsible for iterating through the imported JSONArray and constructing a Movie element for each array element
    public static List<Movie> fromJsonArray(JSONArray movieJsonArray) throws JSONException {
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i < movieJsonArray.length(); i++){
            movies.add(new Movie(movieJsonArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath); // here size is hard-coded to 342 width, and %s refers to the relative posterPath url
            // better practice to fetch all available sizes, append that to base url, and then append relative url
    }

    public String getBackdropPath(){
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public Double getRating(){ return rating; }

    public int getMovieID() {
        return movieID;
    }
}
