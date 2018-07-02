package com.example.rakib.storedata_using_room;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String DATABASE_NAME="movies_db";
    private MovieDatabase movieDatabase;

    Context mContext;

    TextView terminal;
    EditText movieNameField;
    Button clear,show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext=this;

        terminal=findViewById(R.id.textView);
        movieNameField=findViewById(R.id.editText);
        clear=findViewById(R.id.clear_button);
        show=findViewById(R.id.show_button);


        //Initialize Room database
        movieDatabase= Room.databaseBuilder(getApplicationContext() ,MovieDatabase.class  ,DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();







//Data Crud opetation is not permissible in Main Thread of the app..
        //So we are creating Slave Thread



//clear All

        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        movieDatabase.daoAccess().clearMovies();

                        //for Instant showing the update in Terminal
                        List<Movies> movieList=movieDatabase.daoAccess().fetchAllMoviesbyMovie();
                        reTrive(movieList);
                    }
                }).start();

            }
        });

        movieNameField.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(final TextView v, int actionId, KeyEvent event) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Date date= Calendar.getInstance().getTime();
                        int i=movieDatabase.daoAccess().fetchAllMoviesbyMovie().size();

                        Movies movie = new Movies.Builder().withMovieId(i+1).withMovieName(v.getText().toString()).withReleaseDate(date).build();
                        movieDatabase.daoAccess().insertOnlySingleMovie(movie);


                        //for Instant showing the update in Terminal
                        List<Movies> movieList=movieDatabase.daoAccess().fetchAllMoviesbyMovie();
                        reTrive(movieList);

                    }
                }).start();

                return false;
            }
        });

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        List<Movies> movieList=movieDatabase.daoAccess().fetchAllMoviesbyMovie();
                        reTrive(movieList);
                    }
                }).start();

            }
        });
    }



    public void reTrive(List<?> objectList ){
        //DO any Changing before sending to UI Thread
        populateResult(objectList);
    }


    public void populateResult(final List<?> objectList){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String terminalTempText="";
                List<Movies> movies = (List<Movies>) objectList;
                for (Movies movie : movies) {
                    terminalTempText +="" + movie.getMovieId()
                                            + " : " + movie.getMovieName()+" - Rel: "
                                                + movie.getReleaseDate().getDate() +"\n";

                    Toast.makeText(mContext, "" + movie.getMovieId() + " : " + movie.getMovieName(), Toast.LENGTH_SHORT).show();

                }
                terminal.setText("" + terminalTempText);
            }
        });
    }
}
