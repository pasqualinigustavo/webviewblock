package com.webviewblock.components;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.*;
import android.widget.ProgressBar;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.webviewblock.databinding.CustomWebviewBinding;

import kotlin.jvm.JvmOverloads;

public class WebViewCustom extends ConstraintLayout {

    private final String NAME = "Android";
    CustomWebviewBinding binding;
    Context _context;
    WebviewListener listener = null;

    public WebViewCustom(Context context) {
        super(context);
        _context = context;
        init(context, null);
    }

    public WebViewCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        _context = context;
        init(context, attrs);
    }

    public WebViewCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        _context = context;
        init(context, attrs);
    }

    public interface WebviewListener {
        void actionUpdate(String url);
        void actionError();
    }

    private void init(Context context, AttributeSet attrs) {
        binding = CustomWebviewBinding.inflate(LayoutInflater.from(context), this, false);
        addView(binding.getRoot());
    }

    public void setup(boolean blockImages, WebviewListener listener) {
        this.listener = listener;
        binding.customWebview.setWebViewClient(
                new Callback(
                        blockImages,
                        binding.customWebviewLoading,
                        binding.customWebview,
                        listener
                ));
        binding.customWebview.getSettings().setJavaScriptEnabled(true);
        binding.customWebview.getSettings().setAllowContentAccess(true);
        binding.customWebview.getSettings().setAllowFileAccess(true);
        binding.customWebview.getSettings().setDomStorageEnabled(true);
        binding.customWebview.getSettings().setLoadWithOverviewMode(true);
        binding.customWebview.getSettings().setUseWideViewPort(true);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            binding.customWebview.evaluateJavascript("enable();", null);
        } else {
            binding.customWebview.loadUrl("javascript:enable();");
        }
    }

    public void navigateToUrl(String url) {
        binding.customWebview.loadUrl(url);
    }

    public void tryToBack() {
        if (binding.customWebview.canGoBack()) {
            binding.customWebview.goBack();
        }
    }

    private class Callback extends WebViewClient {

        boolean blockImages;
        ProgressBar loadingCard;
        WebView webView;
        WebviewListener listener;

        public Callback(boolean blockImages,
                        ProgressBar loadingCard,
                        WebView webView,
                        WebviewListener listener) {
            this.blockImages = blockImages;
            this.loadingCard = loadingCard;
            this.webView = webView;
            this.listener = listener;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            Log.d("WebviewCustom", "onReceivedSslError");
            handler.proceed();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            loadingCard.setVisibility(View.VISIBLE);
            webView.setVisibility(View.GONE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loadingCard.setVisibility(View.GONE);
            webView.setVisibility(View.VISIBLE);
            listener.actionUpdate(url);
            if (blockImages) {
                /*I don't know if this is the best approach to do it, I searched this command on internet!*/
                webView.loadUrl(
                    "javascript:(function(){ var imgs=document.getElementsByTagName('img');" +
                            "for(i=0;i<imgs.length;i++) { imgs[i].style.display='none'; } })()"
                );
            }
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            Log.d("WebviewCustom", "onReceivedError");
            listener.actionError();
            super.onReceivedError(view, request, error);
        }

        @Override
        public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            Log.d("WebviewCustom", "onReceivedError");
            webView.setVisibility(View.GONE);
            loadingCard.setVisibility(View.GONE);
            listener.actionError();
            super.onReceivedHttpError(view, request, errorResponse);
        }
    }

}
