package com.example.db_detective.core.main

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.db_detective.core.exceptions.NoDatabaseFoundForName
import com.example.db_detective.core.manager.DBManager
import com.example.db_detective.core.manager.DBNotificationManager
import com.example.db_detective.core.manager.IDBManager
import com.example.db_detective.core.manager.IDBNotificationManager
import com.example.db_detective.model.DBDetectiveTableModel

class DBDetective private constructor() {

    private val dbNameAndInstanceMap = mutableMapOf<String, SupportSQLiteDatabase>()
    private val tableNameAndDbNameMap = mutableMapOf<String, String>()

    private val dbManager: IDBManager by lazy { DBManager() }
    private val dbNotificationManager: IDBNotificationManager by lazy { DBNotificationManager() }

    private var isAnyNotificationLiveAlready: Boolean = false


    fun addDatabaseForLogging(dbName: String, db: SupportSQLiteDatabase) {
        dbNameAndInstanceMap[dbName] = db
        if (!isAnyNotificationLiveAlready) {
            Log.d("NotifDebug", "in if")
            getContextForUse?.let {
                isAnyNotificationLiveAlready = true
                Log.d("NotifDebug", "crossed null")

                dbNotificationManager.createNotification(it.invoke())
            }
        }
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
        } ?: DBDetectiveTableModel(0, listOf())
    }

    fun runCustomQueryOnTable(tableName: String, customQuery: String): DBDetectiveTableModel {
        return getDbFromTableName(tableName)?.let {
            dbManager.runCustomQueryOnTable(it, customQuery)
        } ?: DBDetectiveTableModel(0, listOf())
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

        var getContextForUse: (() -> Context)? = null

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

        fun runCustomQueryOnTable(tableName: String, customQuery: String): DBDetectiveTableModel {
            return getInstanceInternal().runCustomQueryOnTable(tableName, customQuery)
        }

        fun notificationRemoved() {
            getInstanceInternal().isAnyNotificationLiveAlready = false
        }
    }

}