package com.example.db_detective.core.utils

import android.content.Context
import androidx.room.RoomDatabase
import com.example.db_detective.core.exceptions.NullDatabaseName
import com.example.db_detective.core.main.DBDetective

fun <DB: RoomDatabase>DB.attachDBDetective(context: Context): DB {
    return this.apply {
        val writableDb = this.openHelper.writableDatabase
        openHelper.databaseName?.let {
            DBDetective.addDatabaseForLogging(context, it, writableDb)
        } ?: throw NullDatabaseName("database name can not be null")
    }
}