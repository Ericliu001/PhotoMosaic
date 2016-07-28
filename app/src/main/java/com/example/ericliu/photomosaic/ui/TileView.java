package com.example.ericliu.photomosaic.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.SurfaceHolder;

import com.example.ericliu.photomosaic.ui.base.RenderView;
import com.example.ericliu.photomosaic.ui.filter.MosaicFilter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by ericliu on 25/07/2016.
 */

public class TileView extends RenderView implements SurfaceHolder.Callback {


    private int mGridWidth = 32;
    private int mGridHeight = 32;

    public enum RenderDirection {
        HORIZONTAL, VERTICAL;
    }

    public TileView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void renderTiles(RenderDirection direction) {
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

                Callable<Collection<Pair<Rect, Bitmap>>> callable = startRenderTask(rowArray);
                addTask(callable);

            }
        }
    }



    private void renderVertically(Rect[][] rects) {

        for (int horIndex = 0; horIndex < rects.length; horIndex++) {
            Callable<Collection<Pair<Rect, Bitmap>>> callable = startRenderTask(rects[horIndex]);
            addTask(callable);
        }
    }

    private Callable<Collection<Pair<Rect, Bitmap>>> startRenderTask(final Rect[] rectArray) {
        if (rectArray.length < 1) {
            return null;
        }

        return new Callable<Collection<Pair<Rect, Bitmap>>>() {
            @Override
            public Collection<Pair<Rect, Bitmap>> call() throws Exception {
                List<Pair<Rect, Bitmap>> pairList = new ArrayList<>();

                for (Rect tileRect : rectArray) {
                    Bitmap tileBitmap = MosaicFilter.createTile(tileRect, mBackgroundBitmap);
                    pairList.add(new Pair(tileRect, tileBitmap));
                }
                return pairList;

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
