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
public class VideoAdapter extends BaseAdapter {
    Context context;
    List<Video> videos;
    LayoutInflater inflater=null;
    //dont need it here ,need it on item clck listner in deatil view
   // private final String BaseUrl="https://www.youtube.com/watch?v=";

    public VideoAdapter(Context context,List<Video> videos){
        this.context=context;
        this.videos=videos;
        this.inflater=( LayoutInflater )context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return videos.size();
    }

    @Override
    public Object getItem(int i) {
        return videos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Holder holder=null;
        if(view==null){
            view=inflater.inflate(R.layout.item_video,viewGroup,false);
            holder= new Holder();
            holder.txt=(TextView)view.findViewById(R.id.video_url) ;
            view.setTag(holder);
        }
        else {
            holder=(Holder)view.getTag();
        }
        Video video=videos.get(i);
        holder.txt.setText("Watch Trailer "+(i+1));
        return view;
    }

    public class Holder{
        TextView txt;

    }
}
