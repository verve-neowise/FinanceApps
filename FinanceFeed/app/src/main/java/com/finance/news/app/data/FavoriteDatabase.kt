package com.finance.news.app.data

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


fun favoritesDatabase() = FavoritesDatabaseDelegate()

class FavoritesDatabaseDelegate {
    operator fun getValue(thisRef: AndroidViewModel, property: Any?): FavoritesDatabase {
        return FavoritesDatabase.instance(thisRef.getApplication())
    }
}

@Database(entities = [Ticket::class], version = 1)
@TypeConverters(Converters::class)
abstract class FavoritesDatabase : RoomDatabase() {

    abstract val favoritesRepository: FavoritesRepository

    companion object {

        @Volatile
        private var instance: FavoritesDatabase? = null

        fun instance(context: Context): FavoritesDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    FavoritesDatabase::class.java,
                    "ticket_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}
