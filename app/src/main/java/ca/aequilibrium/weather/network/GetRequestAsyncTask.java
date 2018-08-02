package ca.aequilibrium.weather.network;

import android.os.AsyncTask;
import android.util.Log;
import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;

import java.net.URL;

public abstract class GetRequestAsyncTask<T> extends AsyncTask<String, Void, T>{

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

    abstract String buildUrl();

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
            String response = readStream(stream, 1024);
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

    private String readStream(InputStream stream, int maxReadSize)
            throws IOException {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] rawBuffer = new char[maxReadSize];
        int readSize;
        StringBuilder buffer = new StringBuilder();
        while (((readSize = reader.read(rawBuffer)) != -1) && maxReadSize > 0) {
            if (readSize > maxReadSize) {
                readSize = maxReadSize;
            }
            buffer.append(rawBuffer, 0, readSize);
            maxReadSize -= readSize;
        }
        return buffer.toString();
    }
}
