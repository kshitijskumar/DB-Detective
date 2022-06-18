package com.example.db_detective.core.manager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.db_detective.R
import com.example.db_detective.core.utils.RemoveNotificationBroadcastReceiver
import com.example.db_detective.ui.activity.DBDetectiveMainActivity

class DBNotificationManager : IDBNotificationManager {

    override fun createNotification(context: Context) {
        createNotificationChannel(context)
        val builder = setNotificationInteractions(context)

        with(NotificationManagerCompat.from(context)) {
            Log.d("NotifDebug", "notify")
            notify(DB_DETECTIVE_NOTIFICATION_ID, builder.build())
        }

    }

    private fun getNotificationBuilder(context: Context): NotificationCompat.Builder {
        return NotificationCompat.Builder(context, DB_DETECTIVE_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_table)
            .setContentTitle("DB Detective")
            .setContentText("Inspecting database...")
            .setStyle(NotificationCompat.InboxStyle())
            .setAutoCancel(false)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "DBDetectiveChannelName"
            val descText = "used for in app DB inspection"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(DB_DETECTIVE_CHANNEL_ID, name, importance).apply {
                description = descText
            }

            val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    private fun setNotificationInteractions(context: Context): NotificationCompat.Builder {
        val tapIntent = Intent(context, DBDetectiveMainActivity::class.java).apply {
//            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val tapPendingIntent =
            PendingIntent.getActivity(context, 0, tapIntent, 0)

        val removeIntent = Intent(context, RemoveNotificationBroadcastReceiver::class.java).apply {
            action = RemoveNotificationBroadcastReceiver.ACTION_REMOVE_NOTIFICATION
            putExtra(RemoveNotificationBroadcastReceiver.ARG_NOTIFICATION_ID, DB_DETECTIVE_NOTIFICATION_ID)
        }

        val removePendingIntent = PendingIntent.getBroadcast(context, 1, removeIntent, 0)

        return getNotificationBuilder(context)
            .setContentIntent(tapPendingIntent)
            .addAction(R.drawable.ic_table, "Stop", removePendingIntent)
    }

    companion object {
        const val DB_DETECTIVE_CHANNEL_ID = "dbDetectiveChannelId"
        const val DB_DETECTIVE_NOTIFICATION_ID = 1
    }
}