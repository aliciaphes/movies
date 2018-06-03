package com.example.android.movies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.movies.R;
import com.example.android.movies.models.Movie;
import com.example.android.movies.utils.Utilities;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    private Movie currentMovie;

    @BindView(R.id.movie_rating)
    RatingBar mRatingBar;

    @BindView(R.id.movie_original_title)
    TextView tvOriginalTitle;

    @BindView(R.id.movie_releasedate)
    TextView tvReleaseDate;

    @BindView(R.id.movie_synopsis)
    TextView tvSynopsis;

    @BindView(R.id.movie_poster)
    ImageView ivMoviePoster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        currentMovie = intent.getParcelableExtra(MainActivity.EXTRA_MOVIE);

        if(currentMovie != null) {

            setTitle(currentMovie.getTitle());

            setMovieUI();

        } else {
            Toast.makeText(this, R.string.movie_null, Toast.LENGTH_SHORT).show();
        }
    }

    private void setMovieUI() {
        tvOriginalTitle.setText(currentMovie.getOriginalTitle());

        formatRating(currentMovie.getRating());

        tvReleaseDate.setText(
                Utilities.formatDate(currentMovie.getReleaseDate())
        );

        Picasso.with(this)
                .load(currentMovie.getPoster())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(ivMoviePoster);

        tvSynopsis.setText(currentMovie.getSynopsis());
    }

    private void formatRating(Double rating) {
        double ratingOutOfFive = (5 * rating) / 10.0;
        // todo: this looks like it could be potentially improved:
        String stringValue = String.format("%.2f", ratingOutOfFive);
        mRatingBar.setRating(Float.valueOf(stringValue));
    }
}
