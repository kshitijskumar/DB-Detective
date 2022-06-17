package com.example.db_detective.core.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.db_detective.core.exceptions.NoDatabaseFoundForName
import com.example.db_detective.core.manager.DBManager
import com.example.db_detective.core.manager.IDBManager
import com.example.db_detective.model.DBDetectiveTableModel

class DBDetective private constructor() {

    private val dbNameAndInstanceMap = mutableMapOf<String, SupportSQLiteDatabase>()
    private val tableNameAndDbNameMap = mutableMapOf<String, String>()

    private val dbManager: IDBManager by lazy { DBManager() }


    fun addDatabaseForLogging(dbName: String, db: SupportSQLiteDatabase) {
        dbNameAndInstanceMap[dbName] = db
    }

    fun getAllDatabaseNames(): List<String> {
        return dbNameAndInstanceMap.keys.toList()
    }

    fun getTableNamesListFromDatabase(dbName: String): List<String> {
        return dbNameAndInstanceMap[dbName]?.let { db ->
            dbManager.getAllTablesForDb(db).also {
                it.forEach { table ->
                    tableNameAndDbNameMap[table] = dbName
                }
            }
        } ?: throw NoDatabaseFoundForName("No database attached with the name $dbName")
    }

    fun getColumnNamesForTable(tableName: String): List<String> {
        return getDbFromTableName(tableName)?.let {
            dbManager.getColumnNamesForTable(it, tableName)
        } ?: listOf()
    }

    fun getEntireDataOfTable(tableName: String): DBDetectiveTableModel {
        return getDbFromTableName(tableName)?.let {
            dbManager.getEntireDataForTable(it, tableName)
        } ?: DBDetectiveTableModel(listOf())
    }

    private fun getDbFromTableName(tableName: String): SupportSQLiteDatabase? {
        return tableNameAndDbNameMap[tableName]?.let { dbName ->
            dbNameAndInstanceMap[dbName]
        }
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        @Volatile
        private var INSTANCE: DBDetective? = null

        private var getContextForUse: (() -> Context)? = null

        fun getInstance(context: Context): DBDetective {
            return INSTANCE ?: synchronized(this) {
                getContextForUse = { context.applicationContext }
                DBDetective().also {
                    INSTANCE = it
                }
            }
        }

        fun getInstanceInternal(): DBDetective {
            return getInstance(getContextForUse!!.invoke())
        }

        fun addDatabaseForLogging(context: Context, dbName: String, db: SupportSQLiteDatabase) {
            getInstance(context).addDatabaseForLogging(dbName, db)
        }

        fun getAllDatabaseNames(): List<String> {
            return getInstanceInternal().getAllDatabaseNames()
        }

        fun getAllTablesFromDatabase(dbName: String): List<String> {
            return getInstanceInternal().getTableNamesListFromDatabase(dbName)
        }

        fun getColumnNamesForTable(tableName: String): List<String> {
            return getInstanceInternal().getColumnNamesForTable(tableName)
        }

        fun getEntireDataOfTable(tableName: String): DBDetectiveTableModel {
            return getInstanceInternal().getEntireDataOfTable(tableName)
        }
    }

}