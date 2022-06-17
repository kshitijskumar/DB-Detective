package com.example.db_detective.core.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase

class DBDetective private constructor() {

    private val dbNameAndInstanceMap = mutableMapOf<String, SupportSQLiteDatabase>()

    fun addDatabaseForLogging(dbName: String, db: SupportSQLiteDatabase) {
        dbNameAndInstanceMap[dbName] = db
    }

    fun getAllDatabaseNames(): List<String> {
        return dbNameAndInstanceMap.keys.toList()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: DBDetective? = null

        fun getInstance(): DBDetective {
            return INSTANCE ?: synchronized(this) {
                DBDetective().also {
                    INSTANCE = it
                }
            }
        }

        fun addDatabaseForLogging(dbName: String, db: SupportSQLiteDatabase) {
            getInstance().addDatabaseForLogging(dbName, db)
        }

        fun getAllDatabaseNames(): List<String> {
            return getInstance().getAllDatabaseNames()
        }
    }

}