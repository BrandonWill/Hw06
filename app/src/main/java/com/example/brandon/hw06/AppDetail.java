package com.example.brandon.hw06;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Brandon on 2/25/2017.
 */

public class AppDetail {
    private String name;
    private double price;
    private String imageURL = null;
    private Bitmap image = null;
    private boolean favorite = false;
    private String currency;

    public static AppDetail createAppDetail(JSONObject js) throws JSONException {
        AppDetail appDetail = new AppDetail();
        appDetail.setName(js.getJSONObject("im:name").getString("label"));
        appDetail.setPrice(js.getJSONObject("im:price").getJSONObject("attributes").getDouble("amount"));
        appDetail.setCurrency(js.getJSONObject("im:price").getJSONObject("attributes").getString("currency"));
        JSONArray imageJSONArray = js.getJSONArray("im:image");
        if (imageJSONArray.length() >= 1) {
            JSONObject imageJSONObject = imageJSONArray.getJSONObject(0);
            appDetail.setImageURL(imageJSONObject.getString("label"));
        }

        return  appDetail;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String info() {
        return name + "\n" + "Price: " + currency + " " + price;
    }
}
