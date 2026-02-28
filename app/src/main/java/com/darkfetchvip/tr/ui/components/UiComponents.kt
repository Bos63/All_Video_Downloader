package com.darkfetchvip.tr.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun NeonGlowButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    TextButton(
        onClick = onClick,
        modifier = modifier.shadow(16.dp, spotColor = Color(0xFF5EDCFF), ambientColor = Color(0xFF9D7BFF))
            .background(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.shapes.large)
    ) { Text(text) }
}

@Composable
fun GlassCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.65f))
    ) { content() }
}

@Composable
fun ScrollableChipsRow(items: List<String>, selected: String, onSelect: (String) -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()).padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            val bg by animateColorAsState(
                if (item == selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                label = "chip"
            )
            Text(
                text = item,
                modifier = Modifier.background(bg, MaterialTheme.shapes.large).clickable { onSelect(item) }
                    .padding(horizontal = 12.dp, vertical = 8.dp)
            )
        }
    }
}

data class QualityItemUi(val value: String, val fps: String, val size: String, val mode: String)

@Composable
fun QualityItem(item: QualityItemUi, selected: Boolean, onClick: () -> Unit) {
    GlassCard(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("${item.value} • ${item.fps}")
            Text("${item.size} • ${item.mode}", color = if (selected) Color(0xFF7BFFB1) else MaterialTheme.colorScheme.onSurface)
        }
    }
}

data class DownloadCardUi(val title: String, val progress: Int, val speed: String, val eta: String)

@Composable
fun DownloadCard(item: DownloadCardUi, icon: ImageVector) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(icon, contentDescription = null, modifier = Modifier.size(18.dp))
                Text(item.title)
            }
            Text("${item.progress}% ${item.speed} ${item.eta}")
        }
    }
}
