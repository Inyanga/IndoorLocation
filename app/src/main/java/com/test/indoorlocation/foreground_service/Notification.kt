package com.test.indoorlocation.foreground_service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import com.test.indoorlocation.MainActivity
import com.test.indoorlocation.R


const val FOREGROUND_CHANNEL = "INDOOR LOCATION SERVICE"
const val FOREGROUND_CHANNEL_NAME = "Indoor location"
const val FOREGROUND_CHANNEL_DESCRIPTION = "Indoor location service"

private fun createChannel(context: Context) {
    val serviceChannel = NotificationChannel(
        FOREGROUND_CHANNEL,
        FOREGROUND_CHANNEL_NAME,
        NotificationManager.IMPORTANCE_DEFAULT
    )
    serviceChannel.description = FOREGROUND_CHANNEL_DESCRIPTION
    val manager = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
    manager.createNotificationChannel(serviceChannel)
}

fun createNotification(context: Context): Notification {
    createChannel(context)
    val pendingIntent: PendingIntent =
        Intent(context, MainActivity::class.java).let { notificationIntent ->
            PendingIntent.getActivity(context, 0, notificationIntent, 0)
        }

    return Notification.Builder(context, FOREGROUND_CHANNEL)
        .setContentTitle(FOREGROUND_CHANNEL_NAME)
        .setContentText(FOREGROUND_CHANNEL_DESCRIPTION)
        .setSmallIcon(R.drawable.ic_marker_stick_red)
        .setContentIntent(pendingIntent)
        .build()
}