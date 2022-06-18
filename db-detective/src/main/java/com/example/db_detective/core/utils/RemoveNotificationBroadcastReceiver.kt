package com.example.db_detective.core.utils

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.example.db_detective.core.main.DBDetective

class RemoveNotificationBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("NotifDebug", "broad")
        if (context == null) return
        val action = intent?.action ?: return
        val notificationId = intent.getIntExtra(ARG_NOTIFICATION_ID, 1)
        val shouldUpdateDBDetective = intent.getBooleanExtra(ARG_SHOULD_UPDATE_DBD, true)

        if (action == ACTION_REMOVE_NOTIFICATION) {
            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(notificationId)
            if (shouldUpdateDBDetective) {
                DBDetective.notificationRemoved()
            }
        }
    }

    companion object {
        const val ACTION_REMOVE_NOTIFICATION = "action_remove_notification"
        const val ARG_NOTIFICATION_ID = "arg_notificationId"
        const val ARG_SHOULD_UPDATE_DBD = "arg_shouldUpdateDBD"
    }
}