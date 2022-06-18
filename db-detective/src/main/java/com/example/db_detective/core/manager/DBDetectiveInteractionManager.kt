package com.example.db_detective.core.manager

import com.example.db_detective.core.main.DBDetective
import com.example.db_detective.core.mappers.toDBDetectiveRowModel
import com.example.db_detective.core.utils.ManagerResult
import com.example.db_detective.model.DBDetectiveRowModel
import java.lang.Exception

class DBDetectiveInteractionManager : IDBDetectiveInteractionManager {

    override fun getAllDatabaseNames(): List<String> {
        return DBDetective.getAllDatabaseNames()
    }

    override fun getTableNamesListFromDatabase(dbName: String): ManagerResult<List<String>, String> {
        return wrapUserRequestsInTryCatch(
            actionOnTry = { DBDetective.getAllTablesFromDatabase(dbName) },
            actionOnCatch = { it.message ?: "Something went wrong" }
        )
    }

    override fun getColumnNamesForTable(tableName: String): ManagerResult<List<String>, String> {
        return wrapUserRequestsInTryCatch(
            actionOnTry = { DBDetective.getColumnNamesForTable(tableName) },
            actionOnCatch = { it.message ?: "Something went wrong" }
        )
    }

    override fun getEntireDataOfTable(tableName: String): ManagerResult<DBDetectiveRowModel, String> {
        return wrapUserRequestsInTryCatch(
            actionOnTry = { DBDetective.getEntireDataOfTable(tableName).toDBDetectiveRowModel()},
            actionOnCatch = { it.message ?: "Something went wrong" }
        )
    }

    override fun runCustomQueryOnTable(
        tableName: String,
        customQuery: String
    ): ManagerResult<DBDetectiveRowModel, String> {
        return wrapUserRequestsInTryCatch(
            actionOnTry = { DBDetective.runCustomQueryOnTable(tableName, customQuery).toDBDetectiveRowModel() },
            actionOnCatch = { it.message ?: "Something went wrong" }
        )
    }

    override fun isContextForUseAvailable(): Boolean {
        return DBDetective.getContextForUse != null
    }

    private fun <SUCCESS, ERROR>wrapUserRequestsInTryCatch(actionOnTry: () -> SUCCESS, actionOnCatch: (Exception) -> ERROR): ManagerResult<SUCCESS, ERROR> {
        return try {
            ManagerResult.Success(actionOnTry.invoke())
        } catch (e: Exception) {
            ManagerResult.Failure(actionOnCatch.invoke(e))
        }
    }
}