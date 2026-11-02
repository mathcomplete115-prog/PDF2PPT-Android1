package com.example.pdf2ppt

import android.app.Activity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.content.Intent
import android.net.Uri
import android.webkit.ValueCallback

class MainActivity : Activity() {
    private lateinit var web: WebView
    private var chooser: ValueCallback<Array<Uri>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        web = WebView(this)
        setContentView(web)

        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true

        web.webViewClient = WebViewClient()

        web.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                view: WebView,
                callback: ValueCallback<Array<Uri>>,
                params: FileChooserParams
            ): Boolean {
                chooser = callback
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                intent.type = "application/pdf"
                intent.addCategory(Intent.CATEGORY_OPENABLE)
                startActivityForResult(intent, 100)
                return true
            }
        }

        web.loadUrl("file:///android_asset/index.html")
    }

    @Deprecated("Deprecated")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100) {
            val result = if (resultCode == RESULT_OK && data?.data != null)
                arrayOf(data.data!!) else null
            chooser?.onReceiveValue(result)
            chooser = null
        }
    }
}
