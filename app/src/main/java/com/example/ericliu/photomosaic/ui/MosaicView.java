package com.example.ericliu.photomosaic.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

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
                int l = mGridWidth * horIndex;
                int t = mGridWidth * verIndex;
                int r = l + mGridWidth;
                if (r > mImageWidth) {
                    r = mImageWidth;
                }
                int b = t + mGridWidth;
                if (b > mImageHeight) {
                    b = mImageHeight;
                }
                int color = bmBaseLayer.getPixel(l, t);
                Rect rect = new Rect(l, t, r, b);
                paint.setColor(color);
                canvas.drawRect(rect, paint);
            }
        }
        canvas.save();
        return bitmap;
    }
}
