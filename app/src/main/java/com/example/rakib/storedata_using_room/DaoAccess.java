package com.example.rakib.storedata_using_room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

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
    Movies fetchOneMoviesbyMovieId(int movieId);
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
