package com.example.brandon.hw06;

import android.content.Context;
import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Brandon on 2/20/2017.
 */

public interface IData {
    public void setupData(ArrayList<AppDetail> appList);
    public void setupImage(int arrayLocation, Bitmap result);
    public Context getContext();
}
