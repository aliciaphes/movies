package com.example.android.movies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.movies.ApiInterface;
import com.example.android.movies.R;
import com.example.android.movies.ServiceGenerator;
import com.example.android.movies.adapters.MoviesAdapter;
import com.example.android.movies.models.Movie;
import com.example.android.movies.utils.Utilities;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private String criterion;

    @BindView(R.id.recycler_view)
    RecyclerView mMovieRecyclerView;

    private MoviesAdapter mMovieAdapter;
    private List<Movie> mMovieList = new ArrayList<>();

    private final String TAG = MainActivity.class.getSimpleName();
    public final static String EXTRA_MOVIE = "MainActivity.EXTRA_MOVIE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        if (criterion == null) {
            criterion = Utilities.getDefaultCriterion();
        }
        setCriterionLabel();


        mMovieAdapter = new MoviesAdapter(mMovieList, this);

        mMovieRecyclerView.setAdapter(mMovieAdapter);
        mMovieRecyclerView.setLayoutManager(new GridLayoutManager(this, numberOfColumns()));

        fetchMovies();
    }

    private int numberOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        //todo: you can change this divider to adjust the size of the poster
        int widthDivider = 400;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        if(nColumns < 2) return 2;
        return nColumns;
    }


    private void fetchMovies() {
        // clean current values:
        mMovieAdapter.notifyItemRangeRemoved(0, mMovieList.size());
        mMovieList.clear();

        //check connectivity:
        if (Utilities.isNetworkAvailable(this) && Utilities.isOnline()) {
            getMovies();
//            String dummyPopularJson = getDummyJson(); //20 movies
//            parseJson(dummyPopularJson);
        } else {
            // launch offline activity:
            Intent intent = new Intent(this, OfflineActivity.class);
            startActivity(intent);
        }
    }

    private void setCriterionLabel() {
        String criterionLabel = Utilities.getCriterionLabel(criterion, this);
        setTitle(getString(R.string.app_name) + ": " + criterionLabel);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.preferences_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.top_rated:
                criterion = Utilities.TOP_RATED;
                break;
            case R.id.most_popular:
                criterion = Utilities.MOST_POPULAR;
                break;
            default:
                return super.onOptionsItemSelected(item);
        }

        setCriterionLabel();
        fetchMovies();
        return true;
    }


    private void parseJson(String dummyPopularJson) {
        try {
            JSONObject jsonObject = new JSONObject(dummyPopularJson);
            JSONArray jsonResultsArray = jsonObject.getJSONArray("results");

            int length = jsonResultsArray.length();
            for (int i = 0; i < length; i++) {
                try {
                    Movie movie = new Movie();

                    JSONObject movieObject = (JSONObject) jsonResultsArray.get(i);

                    String title = movieObject.getString("title");
                    movie.setTitle(title);

                    title = movieObject.getString("original_title");
                    movie.setOriginalTitle(title);

                    String poster = movieObject.getString("poster_path");
                    movie.setPoster(poster);

                    Double rating = movieObject.getDouble("vote_average");
                    movie.setRating(rating);

                    String releaseDate = movieObject.getString("release_date");
                    movie.setReleaseDate(releaseDate);

                    String synopsis = movieObject.getString("overview");
                    movie.setSynopsis(synopsis);

                    mMovieList.add(movie);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getMovies() {

        String language = Utilities.getLanguage();
        String apiKey = Utilities.getAPIkey(this);

        if (criterion != null && apiKey != null) {

            Map<String, String> params = new HashMap<>();
            params.put("api_key", apiKey);
            params.put("language", language);

            ApiInterface apiInterface = ServiceGenerator.createService(ApiInterface.class);
            Call<List<Movie>> call = apiInterface.getMovies(criterion, params);
            Log.i(TAG, call.request().url().toString());
            call.enqueue(new Callback<List<Movie>>() {
                @Override
                public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            for (Movie movie : response.body()) {
                                movie.setPoster(movie.getPoster());
                                mMovieList.add(movie);
                            }
                        }
                        mMovieAdapter.notifyDataSetChanged();
                    } else {
                        Log.e(TAG, response.message());
                    }
                }

                @Override
                public void onFailure(Call<List<Movie>> call, Throwable t) {
                    Log.e(TAG, t.getMessage());
                }
            });
        }
    }


    private String getDummyJson() {
        return getString(R.string.twenty_movies);
    }


} // end of class
