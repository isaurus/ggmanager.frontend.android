package com.isaac.ggmanager.ui.home.soloq;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.isaac.ggmanager.core.utils.InsetsUtils;
import com.isaac.ggmanager.databinding.FragmentSoloQBinding;

public class SoloQFragment extends Fragment {

    private FragmentSoloQBinding binding;

    private final String URL = "https://op.gg/";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSoloQBinding.inflate(inflater, container, false);
        InsetsUtils.applySystemWindowInsetsPadding(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpWebView();
    }

    private void setUpWebView() {
        WebView webView = binding.wvSoloq;

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                // Para API 21+ (Lollipop)
                view.loadUrl(request.getUrl().toString());
                return true;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Para compatibilidad con versiones anteriores
                view.loadUrl(url);
                return true;
            }
        });

        webView.loadUrl(URL);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}