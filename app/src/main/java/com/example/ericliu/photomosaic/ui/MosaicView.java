package com.example.ericliu.photomosaic.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import com.example.ericliu.photomosaic.util.BitmapUtils;

/**
 * Created by ericliu on 24/07/2016.
 */

public class MosaicView extends View {
    private Bitmap bmBaseLayer;
    private int mGridWidth = 32;

    public MosaicView(Context context) {
        super(context);
    }

    public MosaicView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bmBaseLayer != null) {
            canvas.drawBitmap(bmBaseLayer, 0, 0, null);
        }
    }


    public void setImageBitmap(Bitmap bm) {
        bmBaseLayer = bm;
        invalidate();
    }

    public void drawMosaic(){
        bmBaseLayer = getGridMosaic();
        invalidate();
    }


    private Bitmap getGridMosaic() {
        if (bmBaseLayer == null) {
            return null;
        }

        int mImageWidth = bmBaseLayer.getWidth();
        int mImageHeight = bmBaseLayer.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(mImageWidth, mImageHeight,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        int horCount = (int) Math.ceil(mImageWidth / (float) mGridWidth);
        int verCount = (int) Math.ceil(mImageHeight / (float) mGridWidth);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        for (int horIndex = 0; horIndex < horCount; ++horIndex) {
            for (int verIndex = 0; verIndex < verCount; ++verIndex) {
                int left = mGridWidth * horIndex;
                int top = mGridWidth * verIndex;
                int right = left + mGridWidth;
                if (right > mImageWidth) {
                    right = mImageWidth;
                }
                int bottom = top + mGridWidth;
                if (bottom > mImageHeight) {
                    bottom = mImageHeight;
                }
                Rect rect = new Rect(left, top, right, bottom);
                int color = BitmapUtils.getAverageColor(bmBaseLayer, rect);
                paint.setColor(color);
                canvas.drawRect(rect, paint);
            }
        }
        canvas.save();
        return bitmap;
    }
}
