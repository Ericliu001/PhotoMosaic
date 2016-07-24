package com.example.ericliu.photomosaic.util;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;

/**
 * Created by ericliu on 24/07/2016.
 */

public final class BitmapUtils {
    private BitmapUtils() {
    }


    public static int getAverageColor(Bitmap bitmap, Rect rect) {
        int width = rect.width();
        int height = rect.height();
        int[] outPixels = new int[width * height];

        bitmap.getPixels(outPixels, 0, width, rect.left, rect.top, width, height);
        long redSum = 0L;
        long blueSum = 0L;
        long greenSum = 0L;

        for (int outPixel : outPixels) {
            redSum += Color.red(outPixel);
            greenSum += Color.green(outPixel);
            blueSum += Color.blue(outPixel);
        }

        return Color.rgb((int)(redSum/outPixels.length), (int)(greenSum/outPixels.length), (int)(blueSum/outPixels.length));


    }
}
