package com.owe.track.data

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


fun debtDatabase() = GoalDatabaseDelegate()

class GoalDatabaseDelegate {
    operator fun getValue(thisRef: AndroidViewModel, property: Any?): DebtDatabase {
        return DebtDatabase.instance(thisRef.getApplication())
    }
}

@Database(entities = [Debt::class, Payment::class], version = 2)
@TypeConverters(Converters::class)
abstract class DebtDatabase : RoomDatabase() {

    abstract val debtRepository: DebtRepository

    companion object {

        @Volatile
        private var instance: DebtDatabase? = null

        fun instance(context: Context): DebtDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    DebtDatabase::class.java,
                    "debt_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}
