package com.example.db_detective.core.manager

import com.example.db_detective.core.utils.ManagerResult
import com.example.db_detective.model.DBDetectiveRowModel

interface IDBDetectiveInteractionManager {

    fun getAllDatabaseNames(): List<String>

    fun getTableNamesListFromDatabase(dbName: String): ManagerResult<List<String>, String>

    fun getColumnNamesForTable(tableName: String): ManagerResult<List<String>, String>

    fun getEntireDataOfTable(tableName: String): ManagerResult<DBDetectiveRowModel, String>

    fun runCustomQueryOnTable(tableName: String, customQuery: String): ManagerResult<DBDetectiveRowModel, String>

    fun isContextForUseAvailable(): Boolean
}