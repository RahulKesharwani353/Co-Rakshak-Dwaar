package com.example.corakshak

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {
     var webView : WebView? = null


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val bundle = intent.extras
        val site = bundle?.getString("site")

        webView = findViewById<WebView>(R.id.wb_webView)!!
        if (site != null) {
            webViewsetup(site)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("SetJavaScriptEnabled")
    private fun webViewsetup(site: String) {


        webView?.apply {
            loadUrl(site)
            webView!!.webViewClient = WebViewClient()
            settings.javaScriptEnabled = true
            settings.safeBrowsingEnabled = true
        }
    }

    // if you press Back button this code will work
    override fun onBackPressed() {
        // if your webview can go back it will go back
        if (webView?.canGoBack() == true)
            webView?.goBack()
        // if your webview cannot go back
        // it will exit the application
        else
            super.onBackPressed()
    }
}