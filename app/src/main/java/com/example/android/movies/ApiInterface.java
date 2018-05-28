package com.example.android.movies;

import com.example.android.movies.models.Movie;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @GET("{criterion}")
    Call<List<Movie>> getMovies(@Path("criterion") String criterion,
                                @QueryMap Map<String, String> options);
}
