package com.example.scanner2.PageViewer;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BaseHttpStack;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

public class CustomVolleyRequest {
    private static Context context;
    private static CustomVolleyRequest customVolleyRequest;
    private ImageLoader imageLoader = new ImageLoader(this.requestQueue, new ImageLoader.ImageCache() {
        private final LruCache<String, Bitmap> cache = new LruCache<>(20);

        public Bitmap getBitmap(String url) {
            return this.cache.get(url);
        }

        public void putBitmap(String url, Bitmap bitmap) {
            this.cache.put(url, bitmap);
        }
    });
    private RequestQueue requestQueue = getRequestQueue();

    private CustomVolleyRequest(Context context) {
        context = context;
    }

    public static synchronized CustomVolleyRequest getInstance(Context context2) {
        CustomVolleyRequest customVolleyRequest2;
        synchronized (CustomVolleyRequest.class) {
            if (customVolleyRequest == null) {
                customVolleyRequest = new CustomVolleyRequest(context);
            }
            customVolleyRequest2 = customVolleyRequest;
        }
        return customVolleyRequest2;
    }

    public RequestQueue getRequestQueue() {
        if (this.requestQueue == null) {
            RequestQueue requestQueue = new RequestQueue(new DiskBasedCache(context.getCacheDir(), 10485760), new BasicNetwork((BaseHttpStack) new HurlStack()));
            this.requestQueue = requestQueue;
            requestQueue.start();
        }
        return this.requestQueue;
    }

    public void addToRequestQueue(Request req) {
        getRequestQueue().add(req);
    }

    public ImageLoader getImageLoader() {
        return this.imageLoader;
    }
}
