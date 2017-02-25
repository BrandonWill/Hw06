package com.example.brandon.hw06;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Brandon on 2/6/2017.
 */

public class AppDetailUtil {

    static public class AppDetailParser {
        static ArrayList<AppDetail> parseAppDetails(String in) throws JSONException {
            ArrayList<AppDetail> appDetailsArrayList = new ArrayList<>();
            JSONObject root = new JSONObject(in);

//            Log.d("Info", root.getJSONObject("feed").toString());
            JSONArray appDetailJSONArray = root.getJSONObject("feed").getJSONArray("entry");

            for (int i = 0; i <appDetailJSONArray.length(); i++) {
                JSONObject newsSourceJSONObject = appDetailJSONArray.getJSONObject(i);
                AppDetail appDetail = AppDetail.createAppDetail(newsSourceJSONObject);
                appDetailsArrayList.add(appDetail);
            }
            return appDetailsArrayList;
        }
    }
}
