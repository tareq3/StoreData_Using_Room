package com.example.rakib.storedata_using_room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Date;

//@Entity(tableName = "movies") //if we want a diffrent name for the table instead of the class name.
@Entity
@TypeConverters(DatetypeConverter.class)
public class Movies {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "movieid") //If we want to add default name for attribute
    private int movieId;

    //@ColumnInfo(name = "moviename")//if we want to use the same Name as field for attribute
    private String movieName;


    @Nullable
    private Date releaseDate;


    public Movies() {
    }
    @Ignore
    public Movies(@NonNull int movieId, String movieName, Date releaseDate) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.releaseDate = releaseDate;
    }

    private Movies(Builder builder) {
        setMovieId(builder.movieId);
        setMovieName(builder.movieName);
        setReleaseDate(builder.releaseDate);
    }

    @NonNull
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(@NonNull int movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    @Nullable
    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(@Nullable Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public static final class Builder {
        private int movieId;
        private String movieName;
        private Date releaseDate;

        public Builder() {
        }

        public Builder withMovieId(int val) {
            movieId = val;
            return this;
        }

        public Builder withMovieName(String val) {
            movieName = val;
            return this;
        }

        public Builder withReleaseDate(Date val) {
            releaseDate = val;
            return this;
        }

        public Movies build() {
            return new Movies(this);
        }
    }
}
