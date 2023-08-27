package com.finance.news.app.data

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class TicketResponse (
    @SerializedName("results") val results: List<Ticket>
)

data class Publisher (
    @PrimaryKey(true)
    @ColumnInfo(name = "publisherId")
    val uid: Int? = null,
    @SerializedName("name") val name: String = "",
    @SerializedName("homepage_url") val homepageUrl: String = "",
    @SerializedName("logo_url") val logoUrl: String = "",
    @SerializedName("favicon_url") val faviconUrl: String = ""
)

@Entity(tableName = "ticket")
data class Ticket (
    @PrimaryKey(true)
    val uid: Int? = null,
    @SerializedName("id")
    val id: String = "",
    @SerializedName("publisher")
    @Embedded
    val publisher: Publisher = Publisher(),
    @SerializedName("title")
    val title: String = "",
    @SerializedName("author")
    val author: String = "",
    @SerializedName("published_utc")
    val publishedUtc: String = "",
    @SerializedName("article_url")
    val articleUrl: String = "",
    @SerializedName("image_url")
    val imageUrl: String = "",
    @SerializedName("description")
    val description: String = ""
) {
    @Ignore
    var isFavorite: Boolean = true
}