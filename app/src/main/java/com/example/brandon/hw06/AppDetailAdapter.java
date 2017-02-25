package com.example.brandon.hw06;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Brandon on 2/20/2017.
 */

public class AppDetailAdapter extends ArrayAdapter<AppDetail> {
    Context context;
    List<AppDetail> appList = new ArrayList<>();


    public AppDetailAdapter(Context context, int resource, List<AppDetail> appList) {
        super(context, resource, appList);
        this.context = context;
        this.appList = appList;
    }

    public AppDetailAdapter(Context context, int resource, List<AppDetail> appList, boolean favoritesOnly) {
        super(context, resource, appList);
        this.context = context;
        if (!favoritesOnly) {
            this.appList = appList;
        } else {
            for (AppDetail appDetail : appList) {
                if (appDetail.isFavorite()) {
                    this.appList.add(appDetail);
                }
            }
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.app_list_layout, parent, false);
        }

        final AppDetail appDetail = this.appList.get(position);
        ImageView appIcon = (ImageView) convertView.findViewById(R.id.appListIcon);
        appIcon.setImageBitmap(appList.get(position).getImage());

        final TextView appDetails = (TextView) convertView.findViewById(R.id.appListTextItem);

        appDetails.setText(this.appList.get(position).info());

        ImageView favoriteIcon = (ImageView) convertView.findViewById(R.id.appListFavoriteIcon);
        if (this.appList.get(position).isFavorite()) {
            favoriteIcon.setImageResource(R.drawable.blackstar);
        } else {
            favoriteIcon.setImageResource(R.drawable.whitestar);
        }

        final View finalConvertView = convertView;
        favoriteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case DialogInterface.BUTTON_POSITIVE:
                                if (!appDetail.isFavorite()) {
                                    appDetail.setFavorite(true);
                                    ImageView favoriteIcon = (ImageView) finalConvertView.findViewById(R.id.appListFavoriteIcon);
                                    favoriteIcon.setImageResource(R.drawable.blackstar);
                                } else {
                                    appDetail.setFavorite(false);
                                    ImageView favoriteIcon = (ImageView) finalConvertView.findViewById(R.id.appListFavoriteIcon);
                                    favoriteIcon.setImageResource(R.drawable.whitestar);
                                }
                                updateFavorites();
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                //No button clicked
                                break;
                        }
                    }
                };

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                if (!appDetail.isFavorite()) {
                    builder.setMessage("Are you sure you want to add this App to favorites?");
                } else {
                    builder.setMessage("Are you sure you want to remove this App from favorites?");
                }
                builder.setTitle("Add to Favorites").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });

        return convertView;
    }

    public void updateFavorites() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        Set<String> favorites = new HashSet<String>();
        for (AppDetail appDetail : appList) {
            if (appDetail.isFavorite()) {
                favorites.add(appDetail.getName());
            }
        }
        editor.putStringSet("favorites", favorites).commit();
    }
}
