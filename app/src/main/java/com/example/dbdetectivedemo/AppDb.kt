package com.example.dbdetectivedemo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.db_detective.core.utils.attachDBDetective
import com.example.dbdetectivedemo.testdb.TestDao
import com.example.dbdetectivedemo.testdb.TestEntity

@Database(
    entities = [TestEntity::class],
    version = 1
)
abstract class AppDb: RoomDatabase() {

    abstract fun getTestDao(): TestDao

    companion object {
        @Volatile
        var INSTANCE: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDb::class.java,
                    "app_db"
                )
                    .build()
                    .attachDBDetective(context)
                    .also { INSTANCE = it }
            }
        }
    }

}