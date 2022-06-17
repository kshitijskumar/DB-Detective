package com.example.db_detective.core.manager

import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.db_detective.core.utils.QueryConstants

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
}