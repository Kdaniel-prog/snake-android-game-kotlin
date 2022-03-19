package hu.bme.aut.android.nagyhazi.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [TopScore::class], version = 1)
abstract class TopScoreListDatabase : RoomDatabase() {
    companion object {
        fun getDatabase(applicationContext: Context): TopScoreListDatabase {
            return Room.databaseBuilder(
                applicationContext,
                TopScoreListDatabase::class.java,
                "TopScore-list"
            ).build()
        }
    }
    abstract fun TopScoreDao(): TopScoreDao
}