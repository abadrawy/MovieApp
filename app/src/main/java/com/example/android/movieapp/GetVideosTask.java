package com.example.android.movieapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

/**
 * Created by aisha on 11/17/2016.
 */
public class GetVideosTask extends AsyncTask<Void, Void, String> {
    Context main;
    String movieID;
    DetailFragment dt;
    String baseUrl="http://api.themoviedb.org/3/movie/";

    public GetVideosTask(Context main,DetailFragment dt,String movieID){
        this.main=main;
        this.dt=dt;
        this.movieID=movieID;

    }
    public boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                main.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null
        // otherwise check if we are connected
        return networkInfo != null && networkInfo.isConnected();
    }
    @Override
    protected void onPreExecute() {
    }
    @Override
    protected String doInBackground(Void... voids) {
        // if(isNetworkAvailable()) {
        ///!!!! if newtork is not working and when  acees ey via resource file

        String SERVER_URl = baseUrl+movieID+"/videos?api_key=9f1853aa5c43812202925ed97327a923";//+ main.getString(R.string.APIKey);
        try {
            URL url = new URL(SERVER_URl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
                bufferedReader.close();
                return stringBuilder.toString();
            } finally {
                urlConnection.disconnect();
            }
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage(), e);
            return null;
        }
        //}
        //else return null;
    }
    @Override
    protected void onPostExecute(String response) {
        List<Video> videos=null;
        if(response == null) {
            response = "THERE WAS AN ERROR";
        }
        try {
            JSONObject obj = new JSONObject(response);
            JSONArray arrResult= (JSONArray)obj.get("results");
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.setDateFormat("M/d/yy hh:mm a");
            Gson gson = gsonBuilder.create();
            videos = Arrays.asList(gson.fromJson(arrResult.toString(), Video[].class));
            //pass the list of movies  to adapter
            //instead if using fragmet we will use deatilview
            //prepare our videoAdapter
           dt.prepareVideoAdap(videos);
        }
        catch(Exception e){
            //log error
        }


     Log.d("videotask",videos.toString());


    }

}
