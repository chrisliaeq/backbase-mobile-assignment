package ca.aequilibrium.weather.managers;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * Currently uses and in-memory cache which should suffice.
 *
 * Created by Chris Li on 2018-08-02.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class CacheManager {

    private static CacheManager INSTANCE = null;

    private LruCache<String, Bitmap> mMemoryCache;

    public static CacheManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CacheManager();
        }

        return INSTANCE;
    }

    private CacheManager() {
        final int cacheSize = (int) (Runtime.getRuntime().maxMemory() / 1024) / 8;
        mMemoryCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(final String key, final Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    public void addBitmapToMemoryCache(String key, Bitmap bitmap) {
        if (getBitmapFromMemCache(key) == null) {
            mMemoryCache.put(key, bitmap);
        }
    }

    public Bitmap getBitmapFromMemCache(String key) {
        return mMemoryCache.get(key);
    }

}
