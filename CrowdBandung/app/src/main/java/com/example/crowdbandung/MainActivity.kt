package com.example.crowdbandung

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.crowdbandung.databinding.ActivityMainBinding
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
    }

    private fun fetchVideoData(): Thread {
        return Thread {
            val url = URL("http://cariloka.com/atcscctvindon/cctvatcsindonlengkap/jabar.json")
            val connection = url.openConnection() as HttpsURLConnection

            if(connection.responseCode == 200) {
                val inputSystem = connection.inputStream
                println(inputSystem.toString())
            }
            else {
                binding.textView.text = "Connection failed"
            }
        }
    }
}