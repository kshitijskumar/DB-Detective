package com.example.db_detective.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.db_detective.core.main.DBDetective
import com.example.db_detective.core.manager.DBNotificationManager
import com.example.db_detective.core.utils.DBDetectiveConstants
import com.example.db_detective.core.utils.RemoveNotificationBroadcastReceiver
import com.example.db_detective.databinding.ActivityDbdetectiveMainBinding

class DBDetectiveMainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDbdetectiveMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDbdetectiveMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!checkIfInspectionStillOnAndHandleCase()) {
            Log.d(
                DBDetectiveConstants.LOG_TAG,
                "columns for test_table: ${DBDetective.getColumnNamesForTable("test_table")}"
            )
        }
    }

    private fun checkIfInspectionStillOnAndHandleCase(): Boolean {
        if (DBDetective.getContextForUse == null) {
            val removeNotificationIntent =
                Intent(this, RemoveNotificationBroadcastReceiver::class.java).apply {
                    action = RemoveNotificationBroadcastReceiver.ACTION_REMOVE_NOTIFICATION
                    putExtra(
                        RemoveNotificationBroadcastReceiver.ARG_NOTIFICATION_ID,
                        DBNotificationManager.DB_DETECTIVE_NOTIFICATION_ID
                    )
                    putExtra(RemoveNotificationBroadcastReceiver.ARG_SHOULD_UPDATE_DBD, false)
                }
            sendBroadcast(removeNotificationIntent)
            Toast.makeText(
                this,
                "The application was killed before and for security purposes we disabled the db inspection, relaunch the app to start the process",
                Toast.LENGTH_LONG
            ).show()
        }

        return DBDetective.getContextForUse == null
    }
}