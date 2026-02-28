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

@Composable
fun SettingsScreen() {
    val wifiOnly = remember { mutableStateOf(true) }
    val bg = remember { mutableStateOf(true) }
    val notif = remember { mutableStateOf(true) }

    LazyColumn(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        item { Text("Ayarlar", fontWeight = FontWeight.Bold) }
        item { SwitchCard("Wi-Fi only", wifiOnly.value) { wifiOnly.value = it } }
        item { SwitchCard("Arka planda indir", bg.value) { bg.value = it } }
        item { SwitchCard("Bildirim", notif.value) { notif.value = it } }
        item { InfoCard("Tema", "Dark / AMOLED / Light") }
        item { InfoCard("Glow yoğunluğu", "%70") }
        item { Text("Sosyal & İletişim", fontWeight = FontWeight.SemiBold, modifier = Modifier.padding(top = 8.dp)) }
        item { LinkCard("Telegram", "https://t.me/placeholder") }
        item { LinkCard("YouTube", "https://youtube.com/@placeholder") }
        item { LinkCard("Instagram", "https://instagram.com/placeholder") }
        item { LinkCard("E-posta", "mailto:support@darkfetchvip.tr") }
        item { LinkCard("Website", "https://darkfetchvip.tr") }
        item { LinkCard("Destek", "https://darkfetchvip.tr/support") }
    }
}

@Composable
private fun SwitchCard(label: String, checked: Boolean, onChecked: (Boolean) -> Unit) {
    GlassCard(Modifier.fillMaxWidth()) {
        androidx.compose.foundation.layout.Row(Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(label)
            Switch(checked = checked, onCheckedChange = onChecked)
        }
    }
}

@Composable
private fun InfoCard(title: String, value: String) {
    GlassCard(Modifier.fillMaxWidth()) { Column(Modifier.padding(10.dp)) { Text(title); Text(value) } }
}

@Composable
private fun LinkCard(title: String, value: String) {
    GlassCard(Modifier.fillMaxWidth().clickable {}) {
        Column(Modifier.padding(10.dp)) {
            Text(title)
            Text(value, color = MaterialTheme.colorScheme.primary)
        }
    }
}
