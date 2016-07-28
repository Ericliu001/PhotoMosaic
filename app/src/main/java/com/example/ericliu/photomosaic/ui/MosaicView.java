package com.example.ericliu.photomosaic.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.SurfaceHolder;

import com.example.ericliu.photomosaic.ui.base.RenderView;
import com.example.ericliu.photomosaic.util.BitmapUtils;

import java.util.concurrent.Callable;

/**
 * Created by ericliu on 25/07/2016.
 */

public class MosaicView extends RenderView implements SurfaceHolder.Callback {


    private int mGridWidth = 32;
    private int mGridHeight = 32;

    public enum RenderDirection {
        HORIZONTAL, VERTICAL;
    }

    public MosaicView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void renderMosaic(RenderDirection direction) {
        if (mBackgroundBitmap == null && mDrawingLayerBitmap != null) {
            return;
        }

        Rect[][] rects = getGridRects(mBackgroundBitmap, mGridWidth, mGridHeight);
        if (rects == null) {
            return;
        }

        if (direction == RenderDirection.HORIZONTAL) {
            renderHorizontally(rects);

        } else if (direction == RenderDirection.VERTICAL) {
            renderVertically(rects);
        }

    }


    private void renderHorizontally(Rect[][] rects) {
        if (rects.length > 0) {

            for (int verIndex = 0; verIndex < rects[0].length; verIndex++) {
                Rect[] rowArray = new Rect[rects.length];
                for (int horIndex = 0; horIndex < rects.length; horIndex++) {
                    rowArray[horIndex] = rects[horIndex][verIndex];
                }

                Callable<Pair<Rect, Bitmap>> callable = startRenderTaskHorizontally(rowArray);
                addTask(callable);

            }
        }
    }


    private Callable<Pair<Rect, Bitmap>> startRenderTaskHorizontally(final Rect[] rowArray) {
        if (rowArray.length < 1) {
            return null;
        }

        return new Callable<Pair<Rect, Bitmap>>() {
            @Override
            public Pair<Rect, Bitmap> call() throws Exception {

                int top = rowArray[0].top;
                int bottom = rowArray[0].bottom;
                int left = 0;
                int right = mDrawingLayerBitmap.getWidth();

                Bitmap rowBitmap = Bitmap.createBitmap(mDrawingLayerBitmap.getWidth(), bottom - top, Bitmap.Config.ARGB_8888);
                Rect rowRect = new Rect(left, top, right, bottom);
                Canvas canvas = new Canvas(rowBitmap);

                for (Rect tileRect : rowArray) {
                    int color = BitmapUtils.getAverageColor(mBackgroundBitmap, tileRect);
                    Paint paint = new Paint();
                    paint.setColor(color);

                    Rect newRect = new Rect(tileRect.left, 0, tileRect.right, tileRect.bottom - tileRect.top);
                    canvas.drawRect(newRect, paint);
                }
                return new Pair<>(rowRect, rowBitmap);

            }
        };
    }

    private void renderVertically(Rect[][] rects) {

        for (int horIndex = 0; horIndex < rects.length; horIndex++) {
            Callable<Pair<Rect, Bitmap>> callable = startRenderTaskVertically(rects[horIndex]);
            addTask(callable);
        }
    }

    private Callable<Pair<Rect, Bitmap>> startRenderTaskVertically(@NonNull final Rect[] rects) {
        if (rects.length < 1) {
            return null;
        }
        return new Callable<Pair<Rect, Bitmap>>() {
            @Override
            public Pair<Rect, Bitmap> call() throws Exception {

                int top = 0;
                int bottom = mDrawingLayerBitmap.getHeight();
                int left = rects[0].left;
                int right = rects[0].right;

                Bitmap columnBitmap = Bitmap.createBitmap(right - left, mDrawingLayerBitmap.getHeight(), Bitmap.Config.ARGB_8888);
                Rect rowRect = new Rect(left, top, right, bottom);
                Canvas canvas = new Canvas(columnBitmap);

                for (Rect tileRect : rects) {
                    int color = BitmapUtils.getAverageColor(mBackgroundBitmap, tileRect);
                    Paint paint = new Paint();
                    paint.setColor(color);

                    Rect newRect = new Rect(0, tileRect.top, tileRect.right - tileRect.left, tileRect.bottom);
                    canvas.drawRect(newRect, paint);
                }
                return new Pair<>(rowRect, columnBitmap);
            }
        };
    }


    public static Rect[][] getGridRects(Bitmap bitmap, int gridWidth, int gridHeight) {
        if (bitmap == null) {
            return null;
        }


        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int horCount = (int) Math.ceil(width / (float) gridWidth);
        int verCount = (int) Math.ceil(height / (float) gridHeight);

        Rect[][] rects = new Rect[horCount][verCount];

        for (int horIndex = 0; horIndex < horCount; ++horIndex) {
            for (int verIndex = 0; verIndex < verCount; ++verIndex) {
                int left = gridWidth * horIndex;
                int top = gridHeight * verIndex;
                int right = left + gridWidth;
                if (right > width) {
                    right = width;
                }
                int bottom = top + gridHeight;
                if (bottom > height) {
                    bottom = height;
                }
                rects[horIndex][verIndex] = new Rect(left, top, right, bottom);

            }
        }
        return rects;
    }
}
