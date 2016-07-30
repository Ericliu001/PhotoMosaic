package com.example.ericliu.photomosaic.ui.filter;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

import com.example.ericliu.photomosaic.util.BitmapUtils;

/**
 * Created by ericliu on 28/07/2016.
 */

public class MosaicFilter implements ImageFilter {


    public Bitmap createTile(Rect tileRect, Bitmap srcBitmap) {
        Bitmap tileBitmap = Bitmap.createBitmap(tileRect.width(), tileRect.height(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(tileBitmap);
        int color = BitmapUtils.getAverageColor(srcBitmap, tileRect);
        Paint paint = new Paint();
        paint.setColor(color);
        Rect newRect = new Rect(0, 0, tileRect.width(), tileRect.height());
        canvas.drawRect(newRect, paint);
        return tileBitmap;
    }
}
