package com.example.ericliu.photomosaic.util;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.math.BigInteger;

/**
 * Created by ericliu on 24/07/2016.
 */

public final class BitmapUtils {
    private BitmapUtils(){}


    /**
     * Returns the average color of a Bitmap Object, it uses BigIntegers to calculate the sum of pixels to prevent integer overload
     * @param bitmap
     * @return
     */
    public static int getAverageColor(@NonNull Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int[] outPixels = new int[width * height];
        bitmap.getPixels(outPixels, 0, width, 0, 0, width, height);
        BigInteger colorSum = new BigInteger("0");

        for (int outPixel : outPixels) {
            colorSum.add(BigInteger.valueOf(outPixel));
        }

        BigInteger average = colorSum.divide(BigInteger.valueOf(outPixels.length));
        return average.intValue();
    }
}
