package com.example

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.viewinterop.AndroidView
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1E293B))
            .systemBarsPadding()
        ) {
          AMathGameScreen()
        }
      }
    }
  }
}

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun AMathGameScreen(modifier: Modifier = Modifier) {
  var webViewInstance by remember { mutableStateOf<WebView?>(null) }

  BackHandler(enabled = webViewInstance?.canGoBack() == true) {
    webViewInstance?.goBack()
  }

  AndroidView(
    modifier = modifier
      .fillMaxSize()
      .testTag("amath_webview"),
    factory = { context ->
      WebView(context).apply {
        layoutParams = ViewGroup.LayoutParams(
          ViewGroup.LayoutParams.MATCH_PARENT,
          ViewGroup.LayoutParams.MATCH_PARENT
        )
        setBackgroundColor(android.graphics.Color.parseColor("#1e293b"))

        settings.apply {
          javaScriptEnabled = true
          domStorageEnabled = true
          allowFileAccess = true
          cacheMode = WebSettings.LOAD_DEFAULT
          mediaPlaybackRequiresUserGesture = false
          useWideViewPort = true
          loadWithOverviewMode = true
        }

        webViewClient = WebViewClient()
        webChromeClient = WebChromeClient()

        loadUrl("file:///android_asset/index.html")
        webViewInstance = this
      }
    },
    update = { webView ->
      webViewInstance = webView
    }
  )
}
