package com.vipin.news

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import org.json.JSONObject as JSONObject

class MainActivity : AppCompatActivity(), NewsItemClick {

    private lateinit var mAdapter: NewsListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter
    }

    private fun fetchData () {
        val url = "https://newsdata.io/api/1/news?apikey=pub_159758fe9a13b0cacb5f70df80c32aefa7138&country=in"
        val jasonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url , null,
            { response ->
                val jsonArray = response.getJSONArray("results")
                val newsArray = ArrayList<NewsModel>()
                for (i in 0 until jsonArray.length()) {
                    val newsJsonObject = jsonArray.getJSONObject(i)
                    val newsModel = NewsModel(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("creator"),
                        newsJsonObject.getString("link"),
                        newsJsonObject.getString("image_url"),
                    )
                    newsArray.add(newsModel)
                }
                mAdapter.updateNews(newsArray)

            },
            {

            })

        // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jasonObjectRequest)
    }

    override fun onItemClicked(item: NewsModel) {

    }
}