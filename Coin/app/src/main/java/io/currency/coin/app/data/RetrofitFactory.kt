package io.currency.coin.app.data

import androidx.lifecycle.AndroidViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun currenciesApi() = CurrenciesApiDelegate()

class CurrenciesApiDelegate {
    operator fun getValue(thisRef: AndroidViewModel, property: Any?): CurrenciesRepository {
        return RetrofitFactory.currenciesRepository
    }
}

object RetrofitFactory {

    private val retrofit: Retrofit
        get() = Retrofit.Builder()
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(
                        HttpLoggingInterceptor().apply {
                            setLevel(HttpLoggingInterceptor.Level.BODY)
                    }).build()
            )
            .baseUrl("http://data.fixer.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val currenciesRepository: CurrenciesRepository
        get() = retrofit.create(CurrenciesRepository::class.java)
}