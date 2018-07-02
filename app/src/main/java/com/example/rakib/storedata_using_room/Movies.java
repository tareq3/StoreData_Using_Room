package com.example.rakib.storedata_using_room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Movies {
    @NonNull
    @PrimaryKey
    private String movieId;

    private String movieName;

    public Movies() {
    }

    private Movies(Builder builder) {
        movieId = builder.movieId;
        movieName = builder.movieName;
    }

    @NonNull
    public String getMovieId() {
        return movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieId(@NonNull String movieId) {
        this.movieId = movieId;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public static final class Builder {
        private String movieId;
        private String movieName;

        public Builder() {
        }

        public Builder withMovieId(String val) {
            movieId = val;
            return this;
        }

        public Builder withMovieName(String val) {
            movieName = val;
            return this;
        }

        public Movies build() {
            return new Movies(this);
        }
    }
}
