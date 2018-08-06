package com.example.android.movieapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aisha on 11/17/2016.
 */
public class ReviewAdapter extends BaseAdapter {
    Context context;
    List<Review> reviews;
    LayoutInflater inflater=null;

    public ReviewAdapter(Context context,List<Review> reviews){
        this.context=context;
        this.reviews=reviews;
        this.inflater=( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return reviews.size();
    }

    @Override
    public Object getItem(int i) {
        return reviews.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder=null;
        if(view==null){
            view=inflater.inflate(R.layout.item_review,viewGroup,false);
            holder= new Holder();
            holder.contnet=(TextView)view.findViewById(R.id.review_content) ;
            holder.author=(TextView)view.findViewById(R.id.review_author) ;
            view.setTag(holder);
        }
        else {
            holder=(Holder)view.getTag();
        }
        Review review=reviews.get(i);
        Log.d("revAdap",review.getAuthor());
        holder.contnet.setText(review.getContent());
        holder.author.setText(review.getAuthor());
        return view;
    }

    public class Holder{
        TextView contnet;
        TextView author;

    }
}

