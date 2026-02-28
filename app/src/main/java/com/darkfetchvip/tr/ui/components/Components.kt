package com.darkfetchvip.tr.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.OutlinedTextField
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
        modifier = modifier
            .shadow(18.dp, shape = MaterialTheme.shapes.large, ambientColor = Color.Cyan, spotColor = Color.Magenta)
            .background(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.shapes.large)
    ) { Text(text, color = MaterialTheme.colorScheme.onPrimaryContainer) }
}

@Composable
fun GlassCard(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.65f)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.4f))
    ) { content() }
}

@Composable
fun ScrollableChipsRow(items: List<String>, selected: String, onSelect: (String) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items.forEach { chip ->
            val bg by animateColorAsState(if (chip == selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant, label = "chip")
            Text(
                text = chip,
                color = if (chip == selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .background(bg, MaterialTheme.shapes.large)
                    .clickable { onSelect(chip) }
                    .padding(horizontal = 14.dp, vertical = 8.dp)
            )
        }
    }
}

data class QualityModel(val label: String, val fps: String, val sizeMb: String, val badge: String)

@Composable
fun QualityItem(item: QualityModel, selected: Boolean, onClick: () -> Unit) {
    GlassCard(modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)) {
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text("${item.label} • ${item.fps}")
            Text("${item.sizeMb} • ${item.badge}", color = if (selected) Color.Green else MaterialTheme.colorScheme.onSurface)
        }
    }
}

data class DownloadUiModel(val title: String, val progress: Float, val speed: String, val eta: String)

@Composable
fun DownloadItem(item: DownloadUiModel, icon: ImageVector) {
    GlassCard(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Icon(icon, contentDescription = null, modifier = Modifier.size(20.dp))
                Text(item.title)
            }
            Text("${(item.progress * 100).toInt()}% • ${item.speed} • ${item.eta}")
        }
    }
}
