package ca.aequilibrium.weather.network;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import ca.aequilibrium.weather.managers.CacheManager;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetBitmapFromUrlAsyncTask extends AsyncTask<String, Void, Bitmap> {

    public interface BitmapListener {

        void onComplete(Bitmap bitmap);
    }
    private static final String TAG = GetBitmapFromUrlAsyncTask.class.getSimpleName();
    private BitmapListener mBitmapListener;

    public void setBitmapListener(BitmapListener listener) {
        mBitmapListener = listener;
    }

    @Override
    protected Bitmap doInBackground(final String... strings) {
        String urlString = strings[0];
        Bitmap bitmap = CacheManager.getInstance().getBitmapFromMemCache(urlString);
        if (bitmap != null) {
            return bitmap;
        } else {
            try {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(input);
                CacheManager.getInstance().addBitmapToMemoryCache(urlString, bitmap);
                return bitmap;
            } catch (Exception e) {
                Log.e(TAG, urlString, e);
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(final Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (mBitmapListener != null) {
            mBitmapListener.onComplete(bitmap);
        }
    }
}
