package io.currency.coin.app.data

import com.google.gson.annotations.SerializedName

data class LatestResponse (
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("date")
    val date: String,
    @SerializedName("rates")
    private val _rates: HashMap<String, Double>,
) {
    val rates: List<Rate>
        get() = _rates.map { (code, rate) -> Rate("", code, rate) }
}

data class Rate(
    val name: String,
    val code: String,
    val rate: Double
)