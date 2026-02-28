package com.darkfetchvip.tr.data.repository

import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.darkfetchvip.tr.worker.DownloadWorker
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadRepository @Inject constructor(
    private val workManager: WorkManager
) {
    fun enqueue(url: String, quality: String, format: String) {
        val request = OneTimeWorkRequestBuilder<DownloadWorker>()
            .setInputData(
                Data.Builder()
                    .putString(DownloadWorker.KEY_URL, url)
                    .putString(DownloadWorker.KEY_QUALITY, quality)
                    .putString(DownloadWorker.KEY_FORMAT, format)
                    .build()
            )
            .build()
        workManager.enqueue(request)
    }
}
