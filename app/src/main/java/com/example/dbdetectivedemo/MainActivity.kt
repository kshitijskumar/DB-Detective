package com.example.dbdetectivedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.db_detective.core.main.DBDetective
import com.example.db_detective.core.utils.DBDetectiveConstants.LOG_TAG
import com.example.dbdetectivedemo.testdb.TestEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = AppDb.getInstance(this)

        CoroutineScope(Dispatchers.IO).launch {
            (1..3).forEach {
                db.getTestDao().insertTestEntity(
                    TestEntity(it.toLong(), "something_$it", "telling_$it")
                )
            }
        }

        val allDbs = DBDetective.getAllDatabaseNames()
        Log.d(LOG_TAG, "dbs: $allDbs")
        allDbs.forEach {
            Log.d(LOG_TAG, "tables: ${DBDetective.getAllTablesFromDatabase(it)}")
        }
        Log.d(LOG_TAG, "columns for test_table: ${DBDetective.getColumnNamesForTable("test_table")}")
        Log.d(LOG_TAG, "data for test_table: ${DBDetective.getEntireDataOfTable("test_table")}")
    }
}