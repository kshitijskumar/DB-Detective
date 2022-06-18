package com.example.db_detective.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.db_detective.R
import com.example.db_detective.core.main.DBDetective
import com.example.db_detective.core.manager.DBNotificationManager
import com.example.db_detective.core.utils.RemoveNotificationBroadcastReceiver
import com.example.db_detective.databinding.ActivityDbdetectiveMainBinding
import com.example.db_detective.navigation.IDBDetectiveNavigation
import com.example.db_detective.ui.TablesDataFragment
import com.example.db_detective.ui.alldbs.AllDatabasesFragment

class DBDetectiveMainActivity : AppCompatActivity(), IDBDetectiveNavigation {

    private lateinit var binding: ActivityDbdetectiveMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDbdetectiveMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (!checkIfInspectionStillOnAndHandleCase()) {
            setupFragment()
        }
    }

    private fun setupFragment() {
        val fragment = AllDatabasesFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment, AllDatabasesFragment.FRAGMENT_TAG)
            .commit()
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

    override fun navigateToTablesDataFragment(tableName: String) {
        val tablesDataFragment = TablesDataFragment.createInstance(tableName)
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, tablesDataFragment, TablesDataFragment.FRAGMENT_TAG)
            .addToBackStack(null)
            .commit()
    }
}