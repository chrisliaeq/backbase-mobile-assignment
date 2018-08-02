package ca.aequilibrium.weather.network;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

public abstract class GetRequestAsyncTask<T> extends AsyncTask<String, Void, T> {

    public interface RequestListener<T> {

        void onComplete(T response);
    }

    private static final String TAG = GetRequestAsyncTask.class.getSimpleName();
    private Class<T> mClass;
    private RequestListener<T> mListener;

    GetRequestAsyncTask(Class<T> clazz) {
        mClass = clazz;
    }

    public void request(RequestListener<T> listener) {
        mListener = listener;
        executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, buildUrl());
    }

    @Override
    protected T doInBackground(String... strings) {
        String urlString = strings[0];
        HttpsURLConnection connection = null;
        try {
            URL url = new URL(urlString);
            connection = (HttpsURLConnection) url.openConnection();
            connection.setReadTimeout(3000);
            connection.setConnectTimeout(3000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.connect();

            int responseCode = connection.getResponseCode();
            if (responseCode != HttpsURLConnection.HTTP_OK) {
                throw new IOException("HTTP error code: " + responseCode);
            }
            InputStream stream = connection.getInputStream();
            String response = readStream(stream);
            return new Gson().fromJson(response, mClass);
        } catch (Exception e) {
            Log.e(TAG, urlString, e);
        }
        return null;
    }

    @Override
    protected void onPostExecute(T response) {
        super.onPostExecute(response);
        if (mListener != null) {
            mListener.onComplete(response);
        }
    }

    abstract String buildUrl();

    private String readStream(InputStream stream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = stream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        return result.toString("UTF-8");
    }
}
