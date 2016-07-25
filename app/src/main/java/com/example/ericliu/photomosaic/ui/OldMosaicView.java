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

public class OldMosaicView extends View {
    private Bitmap bmBaseLayer;
    private int mGridWidth = 32;


    private int mImageWidth;
    private int mImageHeight;

    public OldMosaicView(Context context) {
        super(context);
    }

    public OldMosaicView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (bmBaseLayer != null) {
            canvas.drawBitmap(bmBaseLayer, 0, 0, null);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mImageWidth == 0 || mImageHeight == 0) {
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        } else {
            setMeasuredDimension(mImageWidth, mImageHeight);
        }
    }

    public void setImageBitmap(final Bitmap bm) {
        post(new Runnable() {
            @Override
            public void run() {
                float ratio = bm.getHeight()/(float)bm.getWidth();
                mImageWidth = getWidth();
                mImageHeight = Math.round(mImageWidth * ratio);

                bmBaseLayer = Bitmap.createScaledBitmap(bm, mImageWidth, mImageHeight, false);
                requestLayout();
                invalidate();
            }
        });
    }

    public Bitmap drawMosaic() {
        bmBaseLayer = getGridMosaic();
        invalidate();
        return bmBaseLayer;
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
