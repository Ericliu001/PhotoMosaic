package com.example.ericliu.photomosaic.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.SurfaceHolder;

import com.example.ericliu.photomosaic.R;
import com.example.ericliu.photomosaic.ui.base.RenderView;
import com.example.ericliu.photomosaic.util.BitmapUtils;

import java.util.concurrent.Callable;

/**
 * Created by ericliu on 25/07/2016.
 */

public class MosaicView extends RenderView implements SurfaceHolder.Callback {


    private int mGridWidth = 32;
    private int mGridHeight = 32;

    public MosaicView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void addSomeFutures() {
        // TODO: 28/07/2016 to be removed

        Callable<Pair<Rect, Bitmap>> callable = new Callable<Pair<Rect, Bitmap>>() {
            @Override
            public Pair<Rect, Bitmap> call() throws Exception {
                Thread.sleep(1000);
                Rect rect = new Rect(0, 0, 100, 100);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_check_off_selected);
                final Pair<Rect, Bitmap> pair = new Pair<>(rect, bitmap);
                return pair;
            }
        };
        addTask(callable);

        Callable<Pair<Rect, Bitmap>> callable2 = new Callable<Pair<Rect, Bitmap>>() {
            @Override
            public Pair<Rect, Bitmap> call() throws Exception {
                Thread.sleep(2000);
                Rect rect = new Rect(100, 0, 200, 100);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_check_off_selected);
                final Pair<Rect, Bitmap> pair = new Pair<>(rect, bitmap);
                return pair;
            }
        };

        addTask(callable2);

        Callable<Pair<Rect, Bitmap>> callable3 = new Callable<Pair<Rect, Bitmap>>() {
            @Override
            public Pair<Rect, Bitmap> call() throws Exception {
                Thread.sleep(3000);
                Rect rect = new Rect(200, 0, 300, 100);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.btn_check_off_selected);
                final Pair<Rect, Bitmap> pair = new Pair<>(rect, bitmap);
                return pair;
            }
        };

        addTask(callable3);

    }

    public void renderVertically() {
        Rect[][] rects = getGridRects(mBackgroundBitmap, mGridWidth, mGridHeight);
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

                Bitmap rowBitmap = Bitmap.createBitmap(mGridWidth, mDrawingLayerBitmap.getHeight(), Bitmap.Config.ARGB_8888);
                Rect rowRect = new Rect(left, top, right, bottom);
                Canvas canvas = new Canvas(rowBitmap);

                for (Rect tileRect : rects) {
                    int color = BitmapUtils.getAverageColor(mBackgroundBitmap, tileRect);
                    Paint paint = new Paint();
                    paint.setColor(color);
                    canvas.drawBitmap(rowBitmap, 0, tileRect.top, paint);
                }
                return new Pair<>(rowRect, rowBitmap);
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
