
package com.example.pdf2ppt

import android.app.Activity
import android.os.Bundle
import android.content.Intent
import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient
import android.webkit.WebChromeClient
import android.webkit.ValueCallback
import android.webkit.WebChromeClient.FileChooserParams
import android.webkit.JavascriptInterface
import android.util.Base64
import android.os.Environment
import android.widget.Toast
import java.io.File

class MainActivity : Activity() {

    private lateinit var web: WebView
    private var chooser: ValueCallback<Array<Uri>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        web = WebView(this)
        setContentView(web)

        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.settings.allowFileAccess = true

        web.addJavascriptInterface(AndroidBridge(), "Android")

        web.webViewClient = WebViewClient()

        web.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                view: WebView?,
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

    inner class AndroidBridge {
        @JavascriptInterface
        fun saveFile(name: String, base64: String) {
            try {
                val folder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
                    ?: throw Exception("Storage folder not available")

                val safeName = name.substringAfterLast("/")
                    .substringAfterLast("\\")
                    .ifBlank { "converted.pptx" }

                val file = File(folder, safeName)
                val bytes = Base64.decode(base64, Base64.DEFAULT)
                file.writeBytes(bytes)

                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "PPT saved: ${file.absolutePath}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            } catch (e: Exception) {
                runOnUiThread {
                    Toast.makeText(
                        this@MainActivity,
                        "Save failed: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    @Deprecated("Deprecated")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100) {
            val result = if (resultCode == RESULT_OK && data?.data != null) {
                arrayOf(data.data!!)
            } else {
                null
            }

            chooser?.onReceiveValue(result)
            chooser = null
        }
    }
}
