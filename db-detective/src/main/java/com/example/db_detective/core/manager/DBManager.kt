package com.example.db_detective.core.manager

import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.db_detective.core.utils.QueryConstants
import com.example.db_detective.model.DBDetectiveColumnModel
import com.example.db_detective.model.DBDetectiveTableModel

class DBManager : IDBManager {

    override fun getAllTablesForDb(db: SupportSQLiteDatabase): List<String> {
        val tablesList = mutableListOf<String>()

        handleCursorActionSafely(
            createCursor = { db.query(QueryConstants.ALL_TABLES_FROM_DB, null) },
            actionToPerform = {
                if (moveToFirst()) {
                    while (!isAfterLast) {
                        val index = getColumnIndex("name")
                        if (index >= 0) {
                            tablesList.add(getString(index))
                        }
                        moveToNext()
                    }
                }
            }
        )

        return tablesList
    }

    override fun getColumnNamesForTable(
        db: SupportSQLiteDatabase,
        tableName: String
    ): List<String> {

        val columnNamesList = mutableListOf<String>()

        handleCursorActionSafely(
            createCursor = { db.query(QueryConstants.COLUMN_NAMES_OF_TABLE.replace("<tableName>", tableName), null) },
            actionToPerform = {
                columnNamesList.addAll(this.columnNames)
            }
        )

        return columnNamesList
    }

    override fun getEntireDataForTable(db: SupportSQLiteDatabase, tableName: String): DBDetectiveTableModel {
        val tempMap = mutableMapOf<String, MutableList<String>>()
        var totalRowsCount = 0
        handleCursorActionSafely(
            createCursor = { db.query("SELECT * FROM $tableName") },
            actionToPerform = {
                if (moveToFirst()) {
                    while (!isAfterLast) {
                        totalRowsCount = this.count
                        (0 until this.columnCount).forEach {
                            val existingData = tempMap[this.getColumnName(it)] ?: mutableListOf()
                            tempMap[this.getColumnName(it)] =
                                existingData.also { list -> list.add(getString(it)) }
                        }
                        moveToNext()
                    }
                }
            }
        )
        return DBDetectiveTableModel(
            totaRowsCount = totalRowsCount,
            columns = tempMap.entries.map {
                DBDetectiveColumnModel(
                    columnName = it.key,
                    columnAllValues = it.value
                )
            }
        )
    }
}