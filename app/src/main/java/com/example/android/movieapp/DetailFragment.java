package com.example.android.movieapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aisha on 11/17/2016.
 */
public class DetailFragment extends Fragment {
    private final String BaseUrl="http://image.tmdb.org/t/p/";
    private final String BaseUrlV="https://www.youtube.com/watch?v=";
    private final String size="w780/";
    SharedPreferences preferences;
    ImageButton imgBtnFav;
    ImageButton imgBtnDel;
    View rootView;
    private Uri uri;


    public DetailFragment(){


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         rootView= inflater.inflate(R.layout.fragment_detail_view, container, false);
        //create shread prefrene
        preferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        imgBtnFav =(ImageButton) rootView.findViewById(R.id.fav);
        imgBtnDel=(ImageButton)rootView.findViewById(R.id.del);


        //get moview for intetnt read it's content
        //then change the ui
        Bundle b=getArguments();
        if(b!=null) {

            final Movie movie = b.getParcelable("movie");

            //print movie object


            //check if the movie is a favroute if so show delee icon
            if (inDB(movie)) {
                //show delete
                showDel();
            } else {
                //show fav
                showFav();
            }


            imgBtnFav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    insert(movie);

                }
            });

            imgBtnDel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   delete(movie);

                }
            });


            ImageView img = (ImageView) rootView.findViewById(R.id.poster);
            TextView overview = (TextView) rootView.findViewById(R.id.overview);
            TextView title = (TextView) rootView.findViewById(R.id.original_title);
            TextView rating = (TextView) rootView.findViewById(R.id.rating);
            TextView release_date = (TextView) rootView.findViewById(R.id.release_date);

            // txt.setMovementMethod(new ScrollingMovementMethod());(attempt to make el text scroable)
            overview.setText(movie.getOverview());
            title.setText(movie.getOriginal_title());
            rating.setText("Rating:" + movie.getVote_average() + "");
            release_date.setText("Release Date:" + movie.getRelease_date());

            Picasso.with(getContext()).load(BaseUrl + size + movie.getPoster_path()).into(img);

            //seed review list and video list
            new GetVideosTask(this.getContext(), this, movie.getId()).execute();
            new GetReviewsTask(this.getContext(), this, movie.getId()).execute();

        }


        return rootView;
    }

    public boolean inPref(String movieId){
        //instead of prefrence use content provider
        return  preferences.contains(movieId);

    }
    public void showFav(){
        imgBtnDel.setVisibility(View.GONE);
        imgBtnFav.setVisibility(View.VISIBLE);

    }
    public void showDel(){
        imgBtnDel.setVisibility(View.VISIBLE);
        imgBtnFav.setVisibility(View.GONE);
    }

    public void insert(Movie movie){
        String id=movie.getId();
        String original_title=movie.getOriginal_title();
        String overview=movie.getOverview();
        String poster_path=movie.getPoster_path();
        String release_date=movie.getRelease_date();
        double vote_average=movie.getVote_average();

        ContentValues values = new ContentValues();
        values.put(MoviesDatabaseHelper.COLUMN_ID, id);
        values.put(MoviesDatabaseHelper.COLUMN_ORG_TITLE, original_title);
        values.put(MoviesDatabaseHelper.COLUMN_OVERVIEW, overview);
        values.put(MoviesDatabaseHelper.COLUMN_POSTER_PATH, poster_path);
        values.put(MoviesDatabaseHelper.COLUMN_RELEASE_DATE, release_date);
        values.put(MoviesDatabaseHelper.COLUMN_VOTE_AVERAGE, vote_average);

        getContext().getContentResolver().insert(MoviesContentProvider.CONTENT_URI, values);

    }

    public void delete(Movie movie){

        String id=movie.getId();
        String uri=MoviesContentProvider.CONTENT_URI.toString();
        getContext().getContentResolver().delete(Uri.parse(uri+"/"+id),null,null);

    }
    public boolean inDB(Movie movie){
        String id=movie.getId();
        String uri=MoviesContentProvider.CONTENT_URI.toString();

        Cursor cursor=getContext().getContentResolver().query(Uri.parse(uri+"/"+id),null,null,null,null);

        if(cursor.getCount()>0) {
            cursor.close();
            return true;

        }
        else {cursor.close();
            return false;
        }



    }

    public void prepareVideoAdap(final List<Video> videos){
        //print list of videos
        Log.d("videodetail",videos.get(0).getKey());
        ListView listView=(ListView)rootView.findViewById(R.id.videos);
        final VideoAdapter vid=new VideoAdapter(this.getContext(),videos);
        listView.setAdapter(vid);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //should have my youtube intent here
                //opne browser
                String url = BaseUrlV+videos.get(i).getKey();
                Intent intent= new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);

            }
        });


    }
    public void prepareReviewAdap(List<Review> reviews){
        ListView listView=(ListView)rootView.findViewById(R.id.reviews);
        final ReviewAdapter rev=new ReviewAdapter(this.getContext(),reviews);
        listView.setAdapter(rev);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //dont know what should happen when you click on a review yet


            }
        });


    }
}
