package com.finance.news.app.data

import androidx.lifecycle.AndroidViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun newsApi() = NewsApiDelegate()

class NewsApiDelegate {
    operator fun getValue(thisRef: AndroidViewModel, property: Any?): NewsRepository {
        return RetrofitFactory.newsRepository
    }
}

object RetrofitFactory {

    private val retrofit: Retrofit
        get() = Retrofit.Builder()
                .baseUrl("https://api.polygon.io/v2/reference/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    val newsRepository: NewsRepository
        get() = retrofit.create(NewsRepository::class.java)
}