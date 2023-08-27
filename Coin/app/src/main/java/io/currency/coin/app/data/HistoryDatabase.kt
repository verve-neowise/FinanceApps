package io.currency.coin.app.data

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.owe.track.data.Converters

fun historyDatabase() = HistoryDatabaseDelegate()

class HistoryDatabaseDelegate {
    operator fun getValue(thisRef: AndroidViewModel, property: Any?): HistoryDatabase {
        return HistoryDatabase.instance(thisRef.getApplication())
    }
}

@Database(entities = [History::class], version = 2)
@TypeConverters(Converters::class)
abstract class HistoryDatabase : RoomDatabase() {

    abstract val historyRepository: HistoryRepository

    companion object {

        @Volatile
        private var instance: HistoryDatabase? = null

        fun instance(context: Context): HistoryDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDatabase::class.java,
                    "converter_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}
