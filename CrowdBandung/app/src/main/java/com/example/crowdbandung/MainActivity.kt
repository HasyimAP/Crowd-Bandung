package com.example.crowdbandung

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var newRecyclerView: RecyclerView
    lateinit var newAdapter: Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val linkJson = applicationContext.assets
            .open("cctv_link.json").bufferedReader().use {
                it.readText()
            }

        val link = Gson().fromJson(linkJson, CctvLink::class.java)

        newAdapter = Adapter(link)

        newRecyclerView = findViewById(R.id.CctvList)
        newRecyclerView.layoutManager = LinearLayoutManager(this)
        newRecyclerView.adapter = newAdapter

    }

//    private fun fetchVideoData(): Thread {
//        return Thread {
//            val url = URL("http://cariloka.com/atcscctvindon/cctvatcsindonlengkap/jabar.json")
//            val connection = url.openConnection() as HttpsURLConnection
//
//            if(connection.responseCode == 200) {
//                val inputSystem = connection.inputStream
//                println(inputSystem.toString())
//            }
//            else {
//                binding.textView.text = "Connection failed"
//            }
//        }
//    }
}