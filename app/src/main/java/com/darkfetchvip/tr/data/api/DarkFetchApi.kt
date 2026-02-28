package com.darkfetchvip.tr.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class ResolveRequest(val url: String)
data class ResolveResponse(val title: String, val thumbnail: String, val duration: String)
data class DownloadRequest(val url: String, val quality: String, val mode: String)
data class DownloadStatus(val taskId: String, val progress: Int, val speed: String, val eta: String)

interface DarkFetchApi {
    @POST("resolve") suspend fun resolve(@Body body: ResolveRequest): ResolveResponse
    @POST("download") suspend fun download(@Body body: DownloadRequest): Map<String, String>
    @GET("status") suspend fun status(@Query("taskId") taskId: String): DownloadStatus
}
