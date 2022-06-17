package com.example.dbdetectivedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.db_detective.core.main.DBDetective
import com.example.db_detective.core.utils.DBDetectiveConstants

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = AppDb.getInstance(this)

        val allDbs = DBDetective.getAllDatabaseNames()
        Log.d(DBDetectiveConstants.LOG_TAG, "dbs: $allDbs")

    }
}