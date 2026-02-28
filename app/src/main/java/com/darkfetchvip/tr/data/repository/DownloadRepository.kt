package com.darkfetchvip.tr.data.repository

import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.darkfetchvip.tr.worker.DownloadWorker
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadRepository @Inject constructor(private val wm: WorkManager) {
    fun enqueue(url: String, quality: String, mode: String) {
        val req = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(Data.Builder().putString("url", url).putString("quality", quality).putString("mode", mode).build())
            .build()
        wm.enqueue(req)
    }
}
