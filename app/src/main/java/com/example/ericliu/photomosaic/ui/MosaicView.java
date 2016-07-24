package com.example.ericliu.photomosaic.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by ericliu on 24/07/2016.
 */

public class MosaicView extends View {
    private Bitmap bmBaseLayer;

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
}
