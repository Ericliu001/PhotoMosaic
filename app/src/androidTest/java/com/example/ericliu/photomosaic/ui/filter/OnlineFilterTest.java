package com.example.ericliu.photomosaic.ui.filter;

import android.graphics.Bitmap;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

/**
 * Created by ericliu on 30/07/2016.
 */
@SmallTest
public class OnlineFilterTest {
    @Test
    public void createTile() throws Exception {
        Bitmap bmp = OnlineFilter.createTile(null, null);

        assertNotNull(bmp);
    }

}