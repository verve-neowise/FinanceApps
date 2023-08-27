package io.currency.coin.app.data

import retrofit2.http.GET
import retrofit2.http.Query

interface CurrenciesRepository {
    @GET("symbols?access_key=662ecbfb9e4216f92d423fe598cc4c98")
    suspend fun getSymbols(): SymbolResponse

    @GET("latest?access_key=662ecbfb9e4216f92d423fe598cc4c98")
    suspend fun getLatest(
        @Query("symbols") symbols: String,
    ): LatestResponse


    @GET("convert?access_key=662ecbfb9e4216f92d423fe598cc4c98")
    suspend fun convert(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("amount") amount: Double,
    ): ConvertResponse
}