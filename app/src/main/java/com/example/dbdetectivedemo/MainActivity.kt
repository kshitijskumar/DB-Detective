package com.example.dbdetectivedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.db_detective.core.main.DBDetective
import com.example.db_detective.core.utils.DBDetectiveConstants.LOG_TAG

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = AppDb.getInstance(this)

        val allDbs = DBDetective.getAllDatabaseNames()
        Log.d(LOG_TAG, "dbs: $allDbs")
        allDbs.forEach {
            Log.d(LOG_TAG, "tables: ${DBDetective.getAllTablesFromDatabase(it)}")
        }
        Log.d(LOG_TAG, "columns for test_table: ${DBDetective.getColumnNamesForTable("test_table")}")
    }
}