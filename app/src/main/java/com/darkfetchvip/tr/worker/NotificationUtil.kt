package com.darkfetchvip.tr.worker

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import com.darkfetchvip.tr.R

object NotificationUtil {
    const val CH = "darkfetch_download"
    const val ID = 100

    fun create(context: Context, progress: Int): Notification {
        val nm = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            nm.createNotificationChannel(NotificationChannel(CH, "DarkFetch", NotificationManager.IMPORTANCE_LOW))
        }
        return NotificationCompat.Builder(context, CH)
            .setSmallIcon(R.mipmap.logo)
            .setContentTitle("DarkFetch indiriyor")
            .setContentText("İlerleme: $progress%")
            .setProgress(100, progress, false)
            .setOngoing(progress < 100)
            .build()
    }
}
