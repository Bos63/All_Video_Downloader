package com.darkfetchvip.tr.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay

@HiltWorker
class DownloadWorker @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters
) : CoroutineWorker(appContext, params) {
    override suspend fun doWork(): Result {
        for (p in 0..100 step 5) {
            setForeground(createForegroundInfo(p))
            setProgress(androidx.work.workDataOf("progress" to p))
            delay(150)
        }
        return Result.success()
    }

    private fun createForegroundInfo(progress: Int): ForegroundInfo {
        return ForegroundInfo(NotificationUtil.ID, NotificationUtil.create(applicationContext, progress))
    }
}
