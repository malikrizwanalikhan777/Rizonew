package com.example

import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun PlayerScreen(url: String) {
  AndroidView(factory = { context ->
    WebView(context).apply {
      layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
      webViewClient = object : WebViewClient() {
        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
          handler?.proceed()
        }
      }
      settings.javaScriptEnabled = true
      settings.mediaPlaybackRequiresUserGesture = false
      if (url.contains("ch=1")) {
        settings.userAgentString = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"
      }
      loadUrl(url)
    }
  })
}
