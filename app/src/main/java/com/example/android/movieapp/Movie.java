package com.example.android.movieapp;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by aisha on 10/20/2016.
 */
public class Movie implements Parcelable {

    String id;
    String original_title;
    String overview;
    String poster_path;
    String release_date;
    double vote_average;

    public Movie(String id,String original_title,String overview,String poster_path,String release_date,double vote_average){
        this.id=id;
        this.original_title=original_title;
        this.overview=overview;
        this.poster_path=poster_path;
        this.release_date=release_date;
        this.vote_average=vote_average;

    }


    public Movie(Parcel in){
        this.id=in.readString();
        this.original_title=in.readString();
        this.overview=in.readString();
        this.poster_path=in.readString();
        this.release_date=in.readString();
        this.vote_average=in.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.original_title);
        parcel.writeString(this.overview);
        parcel.writeString(this.poster_path);
        parcel.writeString(this.release_date);
        parcel.writeDouble(this.vote_average);

    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }
    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(int vote_average) {
        this.vote_average = vote_average;
    }

}
