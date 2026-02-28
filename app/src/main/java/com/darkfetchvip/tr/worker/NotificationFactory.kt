package com.darkfetchvip.tr.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.darkfetchvip.tr.R

object NotificationFactory {
    const val CHANNEL = "darkfetch_downloads"
    const val ID = 51

    fun create(context: Context, progress: Int): Notification {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm.createNotificationChannel(NotificationChannel(CHANNEL, "DarkFetch Downloads", NotificationManager.IMPORTANCE_LOW))
        }
        return NotificationCompat.Builder(context, CHANNEL)
            .setSmallIcon(R.mipmap.logo)
            .setContentTitle("DarkFetch indiriyor")
            .setContentText("İlerleme: $progress%")
            .setOnlyAlertOnce(true)
            .setOngoing(progress < 100)
            .setProgress(100, progress, false)
            .build()
    }
}
