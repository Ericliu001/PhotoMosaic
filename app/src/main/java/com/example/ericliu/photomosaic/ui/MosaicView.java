package com.example.ericliu.photomosaic.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Pair;
import android.view.SurfaceHolder;

import com.example.ericliu.photomosaic.R;
import com.example.ericliu.photomosaic.ui.base.RenderView;

import java.util.concurrent.Callable;

/**
 * Created by ericliu on 25/07/2016.
 */

public class MosaicView extends RenderView implements SurfaceHolder.Callback {


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
}
