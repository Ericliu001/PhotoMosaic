package com.example.ericliu.photomosaic.ui.filter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import com.example.ericliu.photomosaic.util.BitmapUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.ericliu.photomosaic.util.NetworkUtil.checkConnection;

/**
 * Created by ericliu on 30/07/2016.
 */

public class OnlineFilter implements ImageFilter {


    /**
     * Fetch dummy images from http://dummyimage.com/
     *
     * @param tileRect
     * @param srcBitmap
     * @return
     * @throws IOException
     */
    @Override
    public Bitmap createTile(Rect tileRect, Bitmap srcBitmap) {
        if (!checkConnection()) {
            return null;
        }

        StringBuilder stringBuilder = new StringBuilder("http://dummyimage.com/");
        stringBuilder.append(tileRect.width());
        stringBuilder.append("x");
        stringBuilder.append(tileRect.height());

        int color = BitmapUtils.getAverageColor(srcBitmap, tileRect);
        stringBuilder.append("/" + Integer.toHexString(color));

        URL url = null;
        Bitmap bmp = null;
        try {
            url = new URL(stringBuilder.toString());
            bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bmp == null) {
            return null;
        }

        return Bitmap.createScaledBitmap(bmp, tileRect.width(), tileRect.height(), false);
    }

    @Override
    public int getGridHeight() {
        return 128;
    }

    @Override
    public int getGridWidth() {
        return 128;
    }


}
