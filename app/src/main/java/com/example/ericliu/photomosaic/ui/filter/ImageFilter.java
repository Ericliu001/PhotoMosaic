package com.example.ericliu.photomosaic.ui.filter;

import android.graphics.Bitmap;
import android.graphics.Rect;

/**
 * Created by ericliu on 30/07/2016.
 */

public interface ImageFilter {

    Bitmap createTile(Rect tileRect, Bitmap srcBitmap);


    int getGridHeight();

    int getGridWidth();
}
