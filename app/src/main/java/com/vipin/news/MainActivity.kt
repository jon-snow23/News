package com.vipin.news

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest


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
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}