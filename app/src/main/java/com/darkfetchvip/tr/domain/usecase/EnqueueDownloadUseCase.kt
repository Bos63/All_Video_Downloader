package com.darkfetchvip.tr.domain.usecase

import com.darkfetchvip.tr.data.repository.DownloadRepository
import javax.inject.Inject

class EnqueueDownloadUseCase @Inject constructor(private val repo: DownloadRepository) {
    operator fun invoke(url: String, quality: String, mode: String) = repo.enqueue(url, quality, mode)
}
