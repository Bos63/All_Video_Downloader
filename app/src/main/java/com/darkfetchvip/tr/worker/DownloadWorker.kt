package com.darkfetchvip.tr.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

@HiltWorker
class DownloadWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        for (progress in 1..100 step 5) {
            setForeground(createForegroundInfo(progress))
            setProgress(workDataOf("progress" to progress))
            delay(100)
        }
        return Result.success()
    }

    private fun createForegroundInfo(progress: Int): ForegroundInfo {
        val notification = NotificationFactory.create(applicationContext, progress)
        return ForegroundInfo(NotificationFactory.ID, notification)
    }

    companion object {
        const val KEY_URL = "key_url"
        const val KEY_QUALITY = "key_quality"
        const val KEY_FORMAT = "key_format"
    }
}
