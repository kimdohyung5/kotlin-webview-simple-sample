package com.kimdo.webviewtest

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebChromeClient
import android.widget.Toast
import com.kimdo.webviewtest.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate( layoutInflater )
        setContentView(binding.root)

        binding.webView.apply {
            loadUrl( "file:///android_asset/hello.html")
            settings.javaScriptEnabled = true
            addJavascriptInterface( MyJavascriptInterface( applicationContext), "MyJavascriptInterface")

            // web쪽의 log 이벤트를 켑처하기 위해서 필요함..
            webChromeClient = object: WebChromeClient() {
                override fun onConsoleMessage(message: String?, lineNumber: Int, sourceID: String?) {
                    Log.i("WebViewConsole", message ?: "")
                }
            }
        }

        binding.button.setOnClickListener {
            binding.webView.evaluateJavascript("document.body.style.background = 'blue';", null)
        }

        binding.button2.setOnClickListener {
            binding.webView.evaluateJavascript("getToastMessage();") { returnValue ->
                Toast.makeText(applicationContext, returnValue, Toast.LENGTH_SHORT).show()
            }
        }

        binding.button3.setOnClickListener {
            val arg1 = "a1"
            val arg2 = "a2"
            binding.webView.evaluateJavascript("concatenateStrings('${arg1}', '${arg2}');") { returnValue ->
                Toast.makeText(applicationContext, returnValue, Toast.LENGTH_SHORT).show()
            }
        }
    }
}