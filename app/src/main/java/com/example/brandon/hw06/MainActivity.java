package com.example.brandon.hw06;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements IData {

    private final String URL = "https://itunes.apple.com/us/rss/toppaidapplications/limit=25/json";
    private ArrayList<AppDetail> appList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        new GetData(MainActivity.this).execute(URL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refreshMenuItem) {
            setContentView(R.layout.loading);
            new GetData(MainActivity.this).execute(URL);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            return true;
        } else if (id == R.id.sortIncreasingMenuItem) {
            Collections.sort(appList, new Comparator<AppDetail>(){
                public int compare(AppDetail s1, AppDetail s2) {
                    return Double.compare(s2.getPrice(), s1.getPrice());
                }
            });

            ListView listView = (ListView) findViewById(R.id.listView);
            AppDetailAdapter adapter = new AppDetailAdapter(this, R.layout.app_list_layout, appList);
            listView.setAdapter(adapter);
            adapter.setNotifyOnChange(true);
        }else if (id == R.id.sortDecreasingMenuItem) {
            Collections.sort(appList, new Comparator<AppDetail>(){
                public int compare(AppDetail s1, AppDetail s2) {
                    return Double.compare(s1.getPrice(), s2.getPrice());
                }
            });

            ListView listView = (ListView) findViewById(R.id.listView);
            AppDetailAdapter adapter = new AppDetailAdapter(this, R.layout.app_list_layout, appList);
            listView.setAdapter(adapter);
            adapter.setNotifyOnChange(true);
        } else if (id == R.id.favoritesMenuItem) {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void setupData(ArrayList<AppDetail> appList) {
        this.appList = appList;

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Set<String> favorites = new HashSet<String>();
        favorites = preferences.getStringSet("favorites", null);

        if (favorites != null) {
            for (AppDetail appDetail : appList) {
                for (String favorite : favorites) {
                    if (appDetail.getName().equals(favorite)) {
                        appDetail.setFavorite(true);
                    }
                }
            }
        }

        for (int i = 0; i < appList.size(); i++) {
            new GetImage(appList.get(i), i, this).execute();
        }
    }

    @Override
    public void setupImage(int arrayLocation, Bitmap result) {
        appList.get(arrayLocation).setImage(result);

        Log.d("Location", "" +arrayLocation + "/" +(appList.size()-1));
        if (arrayLocation == appList.size()-1) {
            for (AppDetail appDetail : appList) {
                Log.d("Name", appDetail.getName());
                Log.d("Price", String.valueOf(appDetail.getPrice()));
                Log.d("ImageURL", appDetail.getImageURL());
                Log.d("Image Height", String.valueOf(appDetail.getImage().getHeight()));
            }

            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            final ListView listView = (ListView) findViewById(R.id.listView);

            AppDetailAdapter adapter = new AppDetailAdapter(this, R.layout.app_list_layout, appList);
            listView.setAdapter(adapter);
            adapter.setNotifyOnChange(true);



        }
    }

    @Override
    public Context getContext() {
        return null;
    }
}
