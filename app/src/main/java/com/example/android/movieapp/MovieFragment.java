package com.example.android.movieapp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MovieFragment extends Fragment {
    //gave an error when it was initlized here

     View rootView;
    boolean isPop=true;
    boolean mTwoPane=false;
    SharedPreferences preferences;
    Context context;
    GridView gridView;


    public Activity getMain() {
        return main;
    }

    public void setMain(MainActivity main) {
        this.main = main;
    }

    MainActivity main;
    Movie movieMain;
    public void setMTwoPane(boolean mTwoPane){
        this.mTwoPane=mTwoPane;
    }
    public Movie getMovie(){
        return movieMain;
    }
    public void setMovie(Movie movie){
        movieMain=movie;

    }

    public MovieFragment() {
        // Required empty public constructor
    }
     public void setOption(boolean isPop){
         this.isPop=isPop;
     }
     public boolean getOption(){
         return this.isPop;
     }


//update view after removing fav


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        preferences=PreferenceManager.getDefaultSharedPreferences(getContext());
        Log.d("mf",preferences+"");
        rootView= inflater.inflate(R.layout.fragment_movie, container, false);
        gridView=(GridView)rootView.findViewById(R.id.grid_movies);

        getMovies();
        return rootView;
    }


    public void getMovies(){

        GetMoviesTask movieTask= new GetMoviesTask(this.getContext(),this);
        movieTask.execute();
    }

    public void prepareAdapter(List<Movie> movies){


        Log.d("fragment",gridView+"");

       final MovieAdapter mov=new MovieAdapter(getContext(),movies);


        gridView.setAdapter(mov);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(mTwoPane==false) {
                    Log.d("main","false");
                    Intent transition = new Intent(getActivity(), DetailView.class);

                    transition.putExtra("movie", (Parcelable) mov.getItem(i));
                    startActivity(transition);
                }
                else {
                    Log.d("fragment","tablet");
                    passMovie( (Movie)mov.getItem(i));

                }

            }
        });


    }
    public void passMovie(Movie movie){

            DetailFragment d=new DetailFragment();
            Bundle b=new Bundle();
            b.putParcelable("movie",movie);
            d.setArguments(b);
            getFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, d)
                    .commit();



    }

    public void getFav(){
        Cursor cursor=getContext().getContentResolver().query(MoviesContentProvider.CONTENT_URI,null,null,null,null);

        ArrayList<Movie> movies = new ArrayList<Movie>();
        while(cursor.moveToNext()) {


            String id=cursor.getString(0);
            String original_title=cursor.getString(1);
            String overview=cursor.getString(2);
            String poster_path=cursor.getString(3);
            String release_date=cursor.getString(4);
            double vote_average=cursor.getDouble(5);
            Movie movie = new Movie(id,original_title,overview,poster_path,release_date,vote_average);

            movies.add(movie);
        }
        cursor.close();

        prepareAdapter(movies);

    }


}
