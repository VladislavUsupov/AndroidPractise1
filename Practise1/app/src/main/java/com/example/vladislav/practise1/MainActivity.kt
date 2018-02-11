package com.example.vladislav.practise1

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        fetchRepositoriesJson()
    }



    fun fetchRepositoriesJson(){

        val client = OkHttpClient()
        val request = Request.Builder()
                .url("https://api.github.com/users/square/repos")
                .build()
        client.newCall(request).enqueue(object: Callback {

            override fun onResponse(call: Call?, response: Response?) {
                val responseText = response?.body()?.string()
                val repos = Gson().fromJson(responseText, GitHubRepositoryInfo.List::class.java)

                runOnUiThread {
                    recyclerView?.adapter = RepositoryAdapter(repos)
                }
            }

            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }
        })
    }
}