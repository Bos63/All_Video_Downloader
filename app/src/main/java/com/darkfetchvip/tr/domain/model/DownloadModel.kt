package com.darkfetchvip.tr.domain.model

data class DownloadModel(
    val id: String,
    val title: String,
    val progress: Int,
    val quality: String,
    val state: String
)
