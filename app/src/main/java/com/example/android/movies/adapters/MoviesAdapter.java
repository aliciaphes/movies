package com.example.android.movies.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.movies.R;
import com.example.android.movies.activities.DetailActivity;
import com.example.android.movies.activities.MainActivity;
import com.example.android.movies.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private Context context;
    private List<Movie> mMovieList;

    public MoviesAdapter(List<Movie> movieList, Context context) {
        this.context = context;
        this.mMovieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        View view = inflater.inflate(R.layout.item_movie, parent, false);

        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = mMovieList.get(position);

        Picasso.with(context)
                .load(movie.getPoster())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)
                .into(holder.ivMoviePoster);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.movie_poster) ImageView ivMoviePoster;

        public MovieViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Movie m = mMovieList.get(position);
            if(m != null) {
                launchDetailActivity(m);
            }
        }

        public void launchDetailActivity(Movie movie){
            Intent intent = new Intent(context, DetailActivity.class);
            // put extra for the movie that was just clicked on
            intent.putExtra(MainActivity.EXTRA_MOVIE, movie);
            context.startActivity(intent);
        }
    }
}
