package com.webviewblock.components

import android.content.Context
import android.graphics.Bitmap
import android.net.http.SslError
import android.util.AttributeSet
import android.view.LayoutInflater
import android.webkit.*
import android.widget.ProgressBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.webviewblock.databinding.CustomWebviewBinding
import com.webviewblock.util.extensions.show

class WebViewCustom @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    var actionUpdate: ((String?) -> Unit)? = null
    var actionError: (() -> Unit)? = null
    var binding: CustomWebviewBinding = CustomWebviewBinding
        .inflate(LayoutInflater.from(context), this, false)

    fun setup(actionUpdate: (String?) -> Unit, actionError: () -> Unit) {
        this.actionError = actionError
        this.actionUpdate = actionUpdate
        binding.customWebview.webViewClient =
            Callback(binding.customWebviewLoading, binding.customWebview, actionError, actionUpdate)
        binding.customWebview.settings.javaScriptEnabled = true
        binding.customWebview.settings.allowContentAccess = true
        binding.customWebview.settings.allowFileAccess = true
        binding.customWebview.settings.domStorageEnabled = true
        binding.customWebview.settings.loadWithOverviewMode = true
        binding.customWebview.settings.useWideViewPort = true
    }

    fun navigateToUrl(url: String, headers: HashMap<String, String>? = null) {
//        headers?.let {
//            binding.customWebview.loadUrl(url, it)
//        }?.run {
            binding.customWebview.loadUrl(url)
//        }
    }

    fun blockImages(block: Boolean) {

    }

    private class Callback(
        val loadingCard: ProgressBar,
        val webView: WebView,
        val actionError: () -> Unit,
        val actionUpdate: (String?) -> Unit
    ) :
        WebViewClient() {
        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler, er: SslError?) {
            handler.proceed()
        }

        override fun shouldOverrideUrlLoading(
            view: WebView?,
            url: String?
        ): Boolean {
            return false
        }

        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
//            loadingCard.show(true)
//            webView.show(false)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
//            loadingCard.show(false)
//            webView.show(true)
            actionUpdate.invoke(url)
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
//            webView.show(false)
//            loadingCard.show(false)
            actionError.invoke()
            super.onReceivedError(view, request, error)
        }

        override fun onReceivedHttpError(
            view: WebView?,
            request: WebResourceRequest?,
            errorResponse: WebResourceResponse?
        ) {
//            webView.show(false)
//            loadingCard.show(false)
            actionError.invoke()
            super.onReceivedHttpError(view, request, errorResponse)
        }
    }

}
