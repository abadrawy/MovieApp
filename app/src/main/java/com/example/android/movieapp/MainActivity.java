package com.example.android.movieapp;


import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends AppCompatActivity {

    MovieFragment m=new MovieFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
           Log.d("main","movie frgmt");
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_container, m)
                    .commit();
        }


        if (findViewById(R.id.movie_detail_container) != null) {
              Log.d("main","2pane");
            m.setMTwoPane(true);
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new DetailFragment())
                        .commit();
            }

        }
        else {
            Log.d("main","phone");

            m.setMTwoPane(false);
        }



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);//Menu Resource, Menu
        //MenuItem myMenuItem = menu.findItem(R.id.action_settings);
        //getMenuInflater().inflate(R.menu.sub_menu, myMenuItem.getSubMenu());
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();

        Log.d("error",item.getTitle()+"");
         if(id==R.id.item2)//top_rated
         {     Log.d("error","inside if");

             m.setOption(false);
             try {
                 m.getMovies();
             }
             catch (Exception e){
                 Log.d("main","stack");
                 e.printStackTrace();
             }

         }
        if(id==R.id.item1)//popular
        {
            m.setOption(true);

            m.getMovies();

        }
        if(id==R.id.item3){//favourite
            m.getFav();

        }

             return super.onOptionsItemSelected(item);
        }

}
