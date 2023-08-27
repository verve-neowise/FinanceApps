package com.finance.news.app.data

import retrofit2.http.GET

interface NewsRepository {
    @GET("news?apiKey=dqGEk1v0qHon11psUpzaq6snFOflxYPg")
    suspend fun getNews(): TicketResponse
}