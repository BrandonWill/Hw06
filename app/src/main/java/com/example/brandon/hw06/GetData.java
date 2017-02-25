package com.example.brandon.hw06;

import android.os.AsyncTask;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Brandon on 2/6/2017.
 */

public class GetData extends AsyncTask<String, Void, String> {
    IData activity;

    public GetData(IData activity) {
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        BufferedReader reader = null;

        try {
            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected  void onPostExecute(String result) {
        try {
            activity.setupData(AppDetailUtil.AppDetailParser.parseAppDetails(result));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
