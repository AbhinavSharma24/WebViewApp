package com.example.webviewapp

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_my_site.*

class MySiteActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_site)

        webView2.webViewClient = WebViewClient()
        webView2.loadUrl("https://abhinavsharma24.github.io/AbhinavSharmaWebsite/")

        val webSettings = webView2.settings
        webSettings.javaScriptEnabled = true

        backBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
        }
    }

    override fun onBackPressed() {
        if (webView2.canGoBack()){
            webView2.goBack()
        }
        else{
            super.onBackPressed()
        }
    }
}
