package com.example.db_detective.core.manager

import android.database.Cursor
import android.util.Log
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.db_detective.core.exceptions.RecordFetchError
import com.example.db_detective.core.utils.DBDetectiveConstants
import com.example.db_detective.model.DBDetectiveTableModel

interface IDBManager {

    fun getAllTablesForDb(db: SupportSQLiteDatabase): List<String>

    fun getColumnNamesForTable(db: SupportSQLiteDatabase, tableName: String): List<String>

    fun getEntireDataForTable(db: SupportSQLiteDatabase, tableName: String): DBDetectiveTableModel

    fun runCustomQueryOnTable(db: SupportSQLiteDatabase, customQuery: String): DBDetectiveTableModel

    fun handleCursorActionSafely(
        createCursor: () -> Cursor,
        actionToPerform: Cursor.() -> Unit
    ) {
        var cursor: Cursor? = null
        try {
            cursor = createCursor.invoke()
            cursor.actionToPerform()
        } catch (e: Exception) {
            Log.d(DBDetectiveConstants.LOG_TAG, "error in getting tables: $e")
            throw RecordFetchError("Something went wrong here")
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
    }

}