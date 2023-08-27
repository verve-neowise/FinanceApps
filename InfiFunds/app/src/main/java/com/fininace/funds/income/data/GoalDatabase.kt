package com.fininace.funds.income.data

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


fun goalDatabase() = GoalDatabaseDelegate()

class GoalDatabaseDelegate {
    operator fun getValue(thisRef: AndroidViewModel, property: Any?): GoalDatabase {
        return GoalDatabase.instance(thisRef.getApplication())
    }
}

@Database(entities = [Goal::class], version = 1)
@TypeConverters(Converters::class)
abstract class GoalDatabase : RoomDatabase() {

    abstract val goalRepository: GoalRepository

    companion object {

        @Volatile
        private var instance: GoalDatabase? = null

        fun instance(context: Context): GoalDatabase {
            return instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    GoalDatabase::class.java,
                    "goal_db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { instance = it }
            }
        }
    }
}
