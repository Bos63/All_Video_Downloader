package com.darkfetchvip.tr.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Download
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.darkfetchvip.tr.ui.components.DownloadItem
import com.darkfetchvip.tr.ui.components.DownloadUiModel
import com.darkfetchvip.tr.ui.components.GlassCard

@Composable
fun DownloadsScreen() {
    val queue = listOf(
        DownloadUiModel("Belgesel 4K", 0.32f, "4.8 MB/s", "02:11"),
        DownloadUiModel("Podcast AAC", 0.80f, "1.3 MB/s", "00:14"),
        DownloadUiModel("Klip 1080p", 0.11f, "7.1 MB/s", "04:27")
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("İndirilenler & Kuyruk", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            GlassCard(modifier = Modifier.padding(top = 8.dp)) {
                Column(Modifier.padding(12.dp)) {
                    Text("Öncelik: Yüksek / Orta / Düşük")
                    Text("İşlemler: Duraklat • Devam • İptal • Sıraya Al")
                }
            }
        }
        items(queue) { DownloadItem(it, Icons.Default.Download) }
    }
}
