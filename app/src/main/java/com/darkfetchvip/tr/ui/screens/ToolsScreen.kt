package com.darkfetchvip.tr.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.darkfetchvip.tr.ui.components.GlassCard

@Composable
fun ToolsScreen() {
    val tools = listOf(
        "Format Dönüştür", "Trim / Kes", "Kapak İndir", "Metadata Düzenle",
        "Dosya Birleştir", "Dosya Yeniden Adlandır", "Hız Testi", "Depolama Analizi"
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Araçlar", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(top = 12.dp)
        ) {
            items(tools) { tool ->
                GlassCard(modifier = Modifier.fillMaxWidth().clickable { }) {
                    Text(tool, modifier = Modifier.padding(20.dp))
                }
            }
        }
    }
}
