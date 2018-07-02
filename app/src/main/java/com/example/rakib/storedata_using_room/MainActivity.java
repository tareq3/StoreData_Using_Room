package com.example.rakib.storedata_using_room;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static final String DATABASE_NAME="movies_db";
    private MovieDatabase movieDatabase;

Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext=this;
        movieDatabase= Room.databaseBuilder(getApplicationContext() ,MovieDatabase.class  ,DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();

 new Thread(new Runnable() {
            @Override
            public void run() {
                if(movieDatabase.daoAccess().fetchOneMoviesbyMovieId(4)==null) {
                    Movies movies = new Movies.Builder().withMovieId("4").withMovieName("The Prisoner").build();

                    try {
                        movieDatabase.daoAccess().insertOnlySingleMovie(movies);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


new Thread(new Runnable() {
    @Override
    public void run() {
    Movies movie =  movieDatabase.daoAccess().fetchOneMoviesbyMovieId(2);

    reTrive(movie);
    }
}).start();

        }



        public void reTrive(Movies movie ){

          populateResult(movie);
        }


        public void populateResult(final Movies movie){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mContext, ""+ movie.getMovieName(), Toast.LENGTH_SHORT).show();

            }
        });
        }
}
