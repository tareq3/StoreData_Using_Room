package com.example.rakib.storedata_using_room;

import androidx.room.Room;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String DATABASE_NAME = "movies_db";
    private MovieDatabase movieDatabase;

    CompositeDisposable mDisposable;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        mDisposable = new CompositeDisposable();

        movieDatabase = Room.databaseBuilder(getApplicationContext(), MovieDatabase.class, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();


        Movies movie = new Movies.Builder().withMovieId("4").withMovieName("The Prisoner").build();


        //check if data exist if its not available than add the data into database
        mDisposable.add(
                //check data is available or not
                movieDatabase.daoAccess().fetchOneMoviesbyMovieId(4).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(m_movie -> {
                    if (m_movie == null) {
                        //add data with Completable action
                        mDisposable.add(Completable.fromAction(() -> movieDatabase.daoAccess().insertOnlySingleMovie(movie))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(
                                        () -> Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show()
                                        , (throwable) -> Toast.makeText(mContext, "" + throwable, Toast.LENGTH_SHORT).show()
                                ));
                    } else {
                        Toast.makeText(mContext, "Data already exist", Toast.LENGTH_SHORT).show();
                    }
                }));


        //for Single and maybe we need to use subscribeOn
        mDisposable.add(movieDatabase.daoAccess().fetchOneMoviesbyMovieId(4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie2 -> {
                    Toast.makeText(mContext, "" + movie2.getMovieName(), Toast.LENGTH_SHORT).show();
                }));


        Movies movi3 = new Movies.Builder().withMovieId("4").withMovieName("The Prisoner_2").build();

        //update data upgrade result info using Completable
        mDisposable.add(

                Completable.fromAction(() -> movieDatabase.daoAccess().updateMovie(movie))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(() -> {
                            Toast.makeText(mContext, "DataUpdated", Toast.LENGTH_SHORT).show();
                        }));

        //for Single and maybe we need to use subscribeOn
        mDisposable.add(movieDatabase.daoAccess().fetchOneMoviesbyMovieId(4)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie2 -> {
                    Toast.makeText(mContext, "" + movie2.getMovieName(), Toast.LENGTH_SHORT).show();
                }));

        //For flowable we dont need subscribeOn
/*     mDisposable.add(  movieDatabase.daoAccess().fetchMovies()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movies -> {
                    Toast.makeText(mContext, "" + movies.size(), Toast.LENGTH_SHORT).show();
                }));*/
    }

    @Override
    protected void onDestroy() {
        if (mDisposable != null && !mDisposable.isDisposed())
            mDisposable.dispose();
        super.onDestroy();

    }
}
