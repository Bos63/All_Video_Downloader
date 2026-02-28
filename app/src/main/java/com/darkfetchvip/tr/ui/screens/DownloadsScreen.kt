package com.darkfetchvip.tr.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.darkfetchvip.tr.ui.components.DownloadCard
import com.darkfetchvip.tr.ui.components.DownloadCardUi

@Composable
fun DownloadsScreen() {
    val list = listOf(
        DownloadCardUi("Music 320kbps", 56, "3.1MB/s", "ETA 00:22"),
        DownloadCardUi("Video 1080p", 13, "7.2MB/s", "ETA 01:40"),
        DownloadCardUi("Playlist #2", 78, "2.5MB/s", "ETA 00:14")
    )
    LazyColumn(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        item { Text("İndirilenler / Kuyruk", fontWeight = FontWeight.Bold) }
        items(list) { DownloadCard(it, Icons.Default.Download) }
    }
}
