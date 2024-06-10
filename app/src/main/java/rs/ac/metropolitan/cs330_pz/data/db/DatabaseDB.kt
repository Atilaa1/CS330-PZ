package rs.ac.metropolitan.cs330_pz.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import rs.ac.metropolitan.cs330_pz.data.db.dao.FlipCardStatisticDao
import rs.ac.metropolitan.cs330_pz.data.db.dao.UserDao
import rs.ac.metropolitan.cs330_pz.data.db.dao.WordGameStatisticDao
import rs.ac.metropolitan.cs330_pz.data.db.entity.FlipCardStatistic
import rs.ac.metropolitan.cs330_pz.data.db.entity.User
import rs.ac.metropolitan.cs330_pz.data.db.entity.WordGameStatistic


@Database(entities = [User::class, FlipCardStatistic::class, WordGameStatistic::class], version = 3, exportSchema = false)
abstract class DatabaseDB : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun flipCardStatisticDao(): FlipCardStatisticDao
    abstract fun wordGameStatisticDao(): WordGameStatisticDao

    companion object {
        @Volatile
        private var INSTANCE: DatabaseDB? = null

        fun getDatabase(context: Context): DatabaseDB {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseDB::class.java,
                    "cs330db"
                )
                    .fallbackToDestructiveMigration()  // Allow destructive migration
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}