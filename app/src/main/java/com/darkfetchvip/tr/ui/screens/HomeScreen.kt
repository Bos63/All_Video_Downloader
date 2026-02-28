package com.darkfetchvip.tr.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.darkfetchvip.tr.ui.components.GlassCard
import com.darkfetchvip.tr.ui.components.NeonGlowButton
import com.darkfetchvip.tr.ui.components.QualityItem
import com.darkfetchvip.tr.ui.components.QualityModel
import com.darkfetchvip.tr.ui.components.ScrollableChipsRow

@Composable
fun HomeScreen() {
    var url by remember { mutableStateOf("") }
    var selectedChip by remember { mutableStateOf("Video") }
    var selectedQuality by remember { mutableStateOf("1080p") }
    var expanded by remember { mutableStateOf(false) }

    val chips = listOf("Video", "Müzik", "Format", "Altyazı", "Kuyruk", "Hız", "Zamanlayıcı", "Depolama", "Tema", "Daha Fazla…")
    val platforms = listOf("YouTube", "Instagram", "TikTok", "Facebook", "X", "Vimeo", "Dailymotion", "SoundCloud")
    val qualities = listOf("144p", "240p", "360p", "480p", "720p", "1080p", "1440p", "2K", "4K", "2000p")
        .map { QualityModel(it, "60fps", "${(150..950).random()} MB", "Video+Ses") }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text("DarkFetch", style = MaterialTheme.typography.headlineLarge, fontWeight = FontWeight.Bold)
            Text("İzinli içerikleri hızlı kaydet", style = MaterialTheme.typography.titleMedium)
        }
        item {
            OutlinedTextField(
                value = url,
                onValueChange = { url = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Doğrudan medya URL") },
                trailingIcon = {
                    Row {
                        IconButton(onClick = { url = "https://example.com/media.mp4" }) { Icon(Icons.Default.ContentPaste, null) }
                        IconButton(onClick = { url = "" }) { Icon(Icons.Default.Clear, null) }
                    }
                }
            )
        }
        item { NeonGlowButton(text = "Çözümle", onClick = {}, modifier = Modifier.fillMaxWidth()) }
        item { ScrollableChipsRow(chips, selectedChip) { selectedChip = it } }
        item {
            ScrollableChipsRow(platforms, platforms.first()) { }
        }
        item {
            GlassCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("Preview", fontWeight = FontWeight.SemiBold)
                    Text("Başlık: Örnek izinli medya")
                    Text("Süre: 05:21 • Boyut Tahmini: 128 MB")
                }
            }
        }
        items(qualities) {
            QualityItem(item = it, selected = selectedQuality == it.label) { selectedQuality = it.label }
        }
        item {
            NeonGlowButton(
                text = if (expanded) "Gelişmişi Gizle" else "Gelişmiş",
                onClick = { expanded = !expanded },
                modifier = Modifier.fillMaxWidth()
            )
        }
        item {
            AnimatedVisibility(visible = expanded) {
                GlassCard(Modifier.fillMaxWidth()) {
                    Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text("Format: MP4 / MKV / WEBM")
                        Text("Audio: AAC / MP3 / FLAC")
                        Text("Altyazı indir • Dosya adı düzenleme • Klasör seçimi")
                        Text("İndirmeyi Kuyruğa Ekle • Zamanlayıcı • Wi‑Fi ile indir")
                    }
                }
            }
        }
    }
}
