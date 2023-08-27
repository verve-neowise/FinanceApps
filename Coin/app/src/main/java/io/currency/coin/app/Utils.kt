package io.currency.coin.app

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.text.SimpleDateFormat
import java.util.Date

val ViewHolder.context: Context
    get() = itemView.context

val EditText.content: String
    get() = text.toString().trim()

fun String.safeToDouble(): Double {
    return try { toDouble() } catch (e: Exception) { 0.0 }
}

fun EditText.onTextChanged(callback: (String) -> Unit) {
    addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            callback(s.toString())
        }

        override fun afterTextChanged(s: Editable?) {
        }
    })
}

@SuppressLint("SimpleDateFormat")
private val format = SimpleDateFormat("dd/MM/yyyy")

fun Date.format(): String {
    return format.format(this)
}
