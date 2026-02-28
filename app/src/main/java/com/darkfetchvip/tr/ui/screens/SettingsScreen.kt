package com.darkfetchvip.tr.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.darkfetchvip.tr.ui.components.GlassCard

object SocialLinks {
    const val telegram = "https://t.me/placeholder"
    const val youtube = "https://youtube.com/@placeholder"
    const val instagram = "https://instagram.com/placeholder"
    const val email = "mailto:support@darkfetchvip.tr"
    const val website = "https://darkfetchvip.tr"
    const val supportForm = "https://darkfetchvip.tr/support"
}

@Composable
fun SettingsScreen() {
    val wifiOnly = remember { mutableStateOf(true) }
    val bgDownload = remember { mutableStateOf(true) }
    val notifications = remember { mutableStateOf(true) }

    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
            Text("Ayarlar", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }
        item { SettingSwitchCard("Wi-Fi only", wifiOnly.value) { wifiOnly.value = it } }
        item { SettingSwitchCard("Arka planda indir", bgDownload.value) { bgDownload.value = it } }
        item { SettingSwitchCard("İndirme bildirimleri", notifications.value) { notifications.value = it } }
        item { BasicSettingCard("Varsayılan video kalitesi", "1080p") }
        item { BasicSettingCard("Varsayılan müzik kalitesi", "320kbps") }
        item { BasicSettingCard("İndirme klasörü", "/Download/DarkFetch") }
        item { BasicSettingCard("Tema", "AMOLED") }
        item { BasicSettingCard("Buton glow seviyesi", "%70") }
        item {
            Text("Sosyal & İletişim", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 8.dp))
        }
        item { LinkCard("Telegram Kanalı", SocialLinks.telegram) }
        item { LinkCard("YouTube Kanalı", SocialLinks.youtube) }
        item { LinkCard("Instagram", SocialLinks.instagram) }
        item { LinkCard("E-posta", SocialLinks.email) }
        item { LinkCard("Web sitesi", SocialLinks.website) }
        item { LinkCard("Destek / İletişim Formu", SocialLinks.supportForm) }
        item { BasicSettingCard("Hakkımızda", "DarkFetch VIP • v1.0.0") }
    }
}

@Composable
private fun SettingSwitchCard(title: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        androidx.compose.foundation.layout.Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(title)
            Switch(checked = checked, onCheckedChange = onChange)
        }
    }
}

@Composable
private fun BasicSettingCard(title: String, value: String) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text(title, fontWeight = FontWeight.Medium)
            Text(value)
        }
    }
}

@Composable
private fun LinkCard(title: String, value: String) {
    GlassCard(modifier = Modifier.fillMaxWidth().clickable { }) {
        Column(Modifier.padding(12.dp)) {
            Text(title, fontWeight = FontWeight.Medium)
            Text(value, color = MaterialTheme.colorScheme.primary)
        }
    }
}
