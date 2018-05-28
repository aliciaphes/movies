package com.example.android.movies;

import com.example.android.movies.models.Movie;
import com.example.android.movies.utils.MovieAPIdeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private final static String BASE_API_URL = "https://api.themoviedb.org/3/movie/";

    private static Gson gson = new GsonBuilder()
                    .registerTypeAdapter(new TypeToken<List<Movie>>(){}.getType(), new MovieAPIdeserializer())
                    .create();

    private static Retrofit retrofit = null;

    public static <T> T createService(Class<T> serviceClass){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_API_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit.create(serviceClass);
    }
}
