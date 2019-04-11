package com.example.rakib.storedata_using_room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

import java.util.List;
//Todo: Create a Dao or Data Access Object for accessing Database
@Dao
public interface DaoAccess {

    //Todo: Crud Operations

    //region Insert Data
    @Insert
    void insertOnlySingleMovie(Movies movies);

    @Insert
    void insertMultipleMovies(List<Movies> movieList);
    //endregion

    //region Read/Get Data
    @Query("SELECT * FROM Movies WHERE movieId = :movieId")
    Maybe<Movies> fetchOneMoviesbyMovieId(int movieId);
    //endregion

    //region Read/Get Data
    @Query("SELECT * FROM Movies")
    Flowable<List<Movies>> fetchMovies();
    //endregion

    //region Update
    @Update
    void updateMovie(Movies movies);
    //endregion

    //region Delete
    @Delete
    void deleteMovie(Movies movies);
    //endregion


}
