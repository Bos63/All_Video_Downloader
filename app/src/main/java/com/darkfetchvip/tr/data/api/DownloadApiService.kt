package com.darkfetchvip.tr.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class ResolveRequest(val url: String)
data class ResolveResponse(val title: String, val thumbnail: String, val duration: String)
data class DownloadRequest(val url: String, val quality: String, val format: String)
data class DownloadStatusResponse(val taskId: String, val progress: Int, val speed: String)

interface DownloadApiService {
    @POST("resolve")
    suspend fun resolve(@Body request: ResolveRequest): ResolveResponse

    @POST("download")
    suspend fun enqueueDownload(@Body request: DownloadRequest): Map<String, String>

    @GET("status")
    suspend fun status(@Query("taskId") taskId: String): DownloadStatusResponse
}
