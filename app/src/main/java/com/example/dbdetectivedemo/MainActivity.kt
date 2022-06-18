package com.example.dbdetectivedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.db_detective.core.main.DBDetective
import com.example.db_detective.core.mappers.toDBDetectiveRowModel
import com.example.db_detective.core.utils.DBDetectiveConstants.LOG_TAG
import com.example.db_detective.ui.adapter.TableLayoutAdapter
import com.example.dbdetectivedemo.databinding.ActivityMainBinding
import com.example.dbdetectivedemo.testdb.TestEntity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val db = AppDb.getInstance(this)
//
//        CoroutineScope(Dispatchers.IO).launch {
//            (1..40).forEach {
//                db.getTestDao().insertTestEntity(
//                    TestEntity(
//                        it.toLong(),
//                        "something_$it",
//                        "telling_$it",
//                        "address_long line address : $it",
//                        dob = "date of birth $it",
//                        status = "Status level : $it"
//                    )
//                )
//            }
//        }
//
//        val allDbs = DBDetective.getAllDatabaseNames()
//        Log.d(LOG_TAG, "dbs: $allDbs")
//        allDbs.forEach {
//            Log.d(LOG_TAG, "tables: ${DBDetective.getAllTablesFromDatabase(it)}")
//        }
//        Log.d(LOG_TAG, "columns for test_table: ${DBDetective.getColumnNamesForTable("test_table")}")
//        val data = DBDetective.getEntireDataOfTable("test_table")
//        Log.d(LOG_TAG, "data for test_table: $data")
//
//        val tableAdapter = TableLayoutAdapter()
//
////        binding.rvTable.apply {
////            layoutManager = LinearLayoutManager(context)
////            adapter = tableAdapter
////        }
////
////        tableAdapter.submitList(data.toDBDetectiveRowModel().rowEntries)
//
//        CoroutineScope(Dispatchers.IO).launch {
//            delay(2000)
//            val data2 = DBDetective.runCustomQueryOnTable("test_table", "SELECT name FROM test_table WHERE id >= 15")
//            withContext(Dispatchers.Main) {
//                Log.d(LOG_TAG, "data for test_table: $data2")
//                tableAdapter.submitList(data2.toDBDetectiveRowModel().rowEntries)
//            }
//        }

    }
}