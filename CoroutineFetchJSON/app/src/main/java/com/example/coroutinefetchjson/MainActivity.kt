package com.example.coroutinefetchjson

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    private lateinit var jsonTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        jsonTextView = findViewById(R.id.jsonTextView)

        val fetchButton: Button = findViewById(R.id.fetchButton)
        fetchButton.setOnClickListener {
            onFetchButtonClick(it)
        }
    }

    private fun onFetchButtonClick(view: View) {

        // Use your own URL here.
        val url = "https://zyasin.com/jsonexample.json"

        lifecycleScope.launch {
            try {
                val result = fetchJsonAsync(url)
                displayJson(result)
            } catch (e: Exception) {
                displayError(e.message)
            }
        }
    }

    private suspend fun fetchJsonFromRemoteUrl(urlString: String): String {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection
        connection.connectTimeout = 5000 // Set connection timeout to 5 seconds
        connection.readTimeout = 5000 // Set read timeout to 5 seconds

        try {
            val inputStream = connection.inputStream
            val reader = BufferedReader(InputStreamReader(inputStream))
            val response = StringBuilder()
            var line: String?
            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }
            return response.toString()
        } finally {
            connection.disconnect()
        }
    }

    private suspend fun fetchJsonAsync(url: String): String = withContext(Dispatchers.IO) {
        return@withContext fetchJsonFromRemoteUrl(url)
    }

    private suspend fun displayJson(json: String) {
        withContext(Dispatchers.Main) {
            jsonTextView.text = json
        }
    }

    private suspend fun displayError(errorMessage: String?) {
        withContext(Dispatchers.Main) {
            Toast.makeText(this@MainActivity, "Error fetching JSON: $errorMessage", Toast.LENGTH_SHORT).show()
        }
    }
}
