package io.currency.coin.app.data

import com.google.gson.annotations.SerializedName

data class SymbolResponse(
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("symbols")
    private val symbols: HashMap<String, String>
) {
    fun symbols(): List<Symbol> {
        return symbols.map { (code, name) -> Symbol(code, name) }.sortedBy { it.code }
    }
}

data class Symbol(
    val code: String,
    val name: String
) {
    override fun toString(): String {
        return code
    }
}