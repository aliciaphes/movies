package com.example.android.movies.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.movies.utils.Utilities;
import com.google.gson.annotations.SerializedName;

public class Movie implements Parcelable {

    @SerializedName("title")
    private String title;

    @SerializedName("original_title")
    private String originalTitle;

    @SerializedName("overview")
    private String synopsis;

    @SerializedName("poster_path")
    private String posterURL;

    @SerializedName("vote_average")
    private Double rating;

    @SerializedName("release_date")
    private String releaseDate;

    public Movie() {
        title = "";
        originalTitle = "";
        synopsis = "";
        posterURL = "";
        rating = 0.0;
        releaseDate = "";
    }


    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    private Movie(Parcel in) {
        title = in.readString();
        originalTitle = in.readString();
        synopsis = in.readString();
        posterURL = in.readString();
        rating = in.readDouble();
        releaseDate = in.readString();
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPoster() {
        return posterURL;
    }

    public void setPoster(String posterId) {
        this.posterURL = Utilities.POSTER_BASE_URL + "/" + Utilities.POSTER_SIZE + posterId;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
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
        dest.writeString(title);
        dest.writeString(originalTitle);
        dest.writeString(synopsis);
        dest.writeString(posterURL);
        dest.writeDouble(rating);
        dest.writeString(releaseDate);
    }


    //Interface that must be implemented and provided as a public CREATOR field that generates instances of the Movie class from a Parcel.
    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        //Create a new instance of the Parcelable class, instantiating it from the given Parcel whose data had previously been written by Parcelable.writeToParcel()
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        //Create a new array of the Parcelable class.
        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
