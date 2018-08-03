package ca.aequilibrium.weather.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import ca.aequilibrium.weather.R;

/**
 * Created by Chris Li on 2018-08-02.
 * Copyright © 2018 Aequilibrium. All rights reserved.
 */
public class HelpFragment extends Fragment {

    public static final String TAG = HelpFragment.class.getSimpleName();

    private WebView mWebView;

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable final ViewGroup container,
            @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);
        mWebView = view.findViewById(R.id.web_view);
        mWebView.loadUrl("file:///android_asset/help.html");
        return view;
    }
}
