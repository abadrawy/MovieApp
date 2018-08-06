package com.example.android.movieapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by aisha on 10/20/2016.
 */
public class MovieAdapter extends BaseAdapter{
    Context context;
    List<Movie> movies;
    LayoutInflater inflater=null;
    private final String BaseUrl="http://image.tmdb.org/t/p/";
    private final String size="w500/";
    public MovieAdapter(Context context,List<Movie> movies){
        this.context=context;
        this.movies=movies;
        this.inflater=( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return movies.size();
    }

    @Override
    public Object getItem(int i) {
        return movies.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Holder holder=null;
        if(view==null){
            view=inflater.inflate(R.layout.item_movie,viewGroup,false);
            holder= new Holder();
            holder.img=(ImageView)view.findViewById(R.id.movie_img);
            //holder.txt=(TextView)view.findViewById(R.id.movie_title) ;
            view.setTag(holder);
        }
        else {
            holder=(Holder)view.getTag();
        }
        Movie movie=movies.get(i);

        //holder.txt.setText(movie.getOriginal_title());
        Picasso.with(context).load(BaseUrl+size+movie.getPoster_path()).into(holder.img);
        return view;
    }

    public class Holder{
        ImageView img;
        //TextView txt;

    }
}
