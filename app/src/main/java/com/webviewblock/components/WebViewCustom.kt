package com.webviewblock.components

import android.content.Context
import android.graphics.Bitmap
import android.net.http.SslError
import android.util.AttributeSet
import android.util.Log
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

    private lateinit var binding: CustomWebviewBinding
    private var actionUpdate: ((String?) -> Unit)? = null
    private var actionError: (() -> Unit)? = null

    init {
        binding = CustomWebviewBinding.inflate(LayoutInflater.from(context), this, false)
        addView(binding.root)
    }

    fun setup(blockImages: Boolean, actionUpdate: (String?) -> Unit, actionError: () -> Unit) {
        this.actionError = actionError
        this.actionUpdate = actionUpdate
        binding.customWebview.webViewClient =
            Callback(
                blockImages,
                binding.customWebviewLoading,
                binding.customWebview,
                actionError,
                actionUpdate
            )
        binding.customWebview.addJavascriptInterface(WebAppInterface(context), NAME)
        binding.customWebview.settings.javaScriptEnabled = true
        binding.customWebview.settings.allowContentAccess = true
        binding.customWebview.settings.allowFileAccess = true
        binding.customWebview.settings.domStorageEnabled = true
        binding.customWebview.settings.loadWithOverviewMode = true
        binding.customWebview.settings.useWideViewPort = true

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            binding.customWebview.evaluateJavascript("enable();", null)
        } else {
            binding.customWebview.loadUrl("javascript:enable();")
        }
    }

    fun navigateToUrl(url: String) {
        binding.customWebview.loadUrl(url)
    }

    fun tryToBack() {
        if (binding.customWebview.canGoBack()) {
            binding.customWebview.goBack()
        }
    }

    private class WebAppInterface(
        val mContext: Context
    ) {
        //future use
    }

    private class Callback(
        val blockImages: Boolean,
        val loadingCard: ProgressBar,
        val webView: WebView,
        val actionError: () -> Unit,
        val actionUpdate: (String?) -> Unit
    ) :
        WebViewClient() {

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler, er: SslError?) {
            Log.d("WebviewCustom", "onReceivedSslError")
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
            loadingCard.show(true)
            webView.show(false)
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            loadingCard.show(false)
            webView.show(true)
            actionUpdate.invoke(url)
            if (blockImages) {
                /*I don't know if this is the best approach to do it, I searched this command on internet!*/
                webView.loadUrl(
                    "javascript:(function(){ var imgs=document.getElementsByTagName('img');" +
                            "for(i=0;i<imgs.length;i++) { imgs[i].style.display='none'; } })()"
                )
            }
        }

        override fun onReceivedError(
            view: WebView?,
            request: WebResourceRequest?,
            error: WebResourceError?
        ) {
            Log.d("WebviewCustom", "onReceivedError")
            actionError.invoke()
            super.onReceivedError(view, request, error)
        }

        override fun onReceivedHttpError(
            view: WebView?,
            request: WebResourceRequest?,
            errorResponse: WebResourceResponse?
        ) {
            Log.d("WebviewCustom", "onReceivedError")
            webView.show(false)
            loadingCard.show(false)
            actionError.invoke()
            super.onReceivedHttpError(view, request, errorResponse)
        }
    }

    companion object {
        const val NAME = "Android"
    }
}
