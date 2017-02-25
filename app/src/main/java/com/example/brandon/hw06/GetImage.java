package com.example.brandon.hw06;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Brandon on 2/6/2017.
 */

public class GetImage extends AsyncTask<String, Void, Bitmap> {
    AppDetail appDetail;
    int arrayLocation;
    IData activity;

    public GetImage(AppDetail appDetail, int arrayLocation, IData activity) {
        this.appDetail = appDetail;
        this.arrayLocation = arrayLocation;
        this.activity = activity;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        InputStream in = null;
        try {
            URL url = new URL(appDetail.getImageURL());
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            in = con.getInputStream();
            Bitmap image = BitmapFactory.decodeStream(in);
            return image;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    protected  void onPostExecute(Bitmap result) {
        if (result == null) {
//            activity.setupImage(arrayLocation, result);
            new GetImage(appDetail, arrayLocation, activity).execute();
            Log.d("GetImage", "Retrying image");
        }else{
            Log.d("GetImage", "Image obtained");
            activity.setupImage(arrayLocation, resize(result, 32, 32));
        }

    }

    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
                float ratioBitmap = (float) width / (float) height;
                float ratioMax = (float) maxWidth / (float) maxHeight;

                int finalWidth = maxWidth;
                int finalHeight = maxHeight;
                if (ratioMax > 1) {
                    finalWidth = (int) ((float) maxHeight * ratioBitmap);
                } else {
                    finalHeight = (int) ((float) maxWidth / ratioBitmap);
                }
                image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
                return image;
        } else {
            return image;
        }
    }
}
