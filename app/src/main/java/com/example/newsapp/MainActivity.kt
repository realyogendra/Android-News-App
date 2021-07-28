package com.example.newsapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : AppCompatActivity(), NewsItemClicked {

    private lateinit var mAdapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = NewsListAdapter(this)
        recyclerView.adapter = mAdapter
    }

    private fun fetchData() {
        val url = "https://www.youtube.com/redirect?event=comments&redir_token=QUFFLUhqbk8zbUhrd2dGcFhsWFBxN2JiME5Sa2hROHF1d3xBQ3Jtc0tsaE1PZkFIQ2dKWTJObV8yUjFSVVh0S0wxMk5KMjN5dzhGS2lqX19Pcl9zNWtvQU1RWUl6UnNkblcwWHdsMXlwdmRyOG02aUdUck9pa1RwclZJM2lvRkg5VnBZYU03cUxWRTVHZXNRUVREV1hLbm5lMA&q=https%3A%2F%2Fsaurav.tech%2FNewsAPI%2Ftop-headlines%2Fcategory%2Fhealth%2Fin.json&stzid=UgwlK7JH-2sIZZq5HXF4AaABAg"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<News>()
                for(i in 0 until newsJsonArray.length()) {
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = News(
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("uUsing Android SDK: C:\\Users\\vgyog\\AppData\\Local\\Android\\Sdk\nrl"),
                        newsJsonObject.getString("urlToImage")
                    )
                    newsArray.add(news)
                }

                mAdapter.updateNews(newsArray)
            },
            Response.ErrorListener {

            }
        )
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
    }

    override fun onItemClicked(item: News) {
        val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))
    }
}