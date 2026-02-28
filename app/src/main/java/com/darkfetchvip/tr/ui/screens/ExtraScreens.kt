package com.darkfetchvip.tr.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun VideoScreen() = PlaceholderScreen("Video İndir")

@Composable
fun AudioScreen() = PlaceholderScreen("Müzik İndir")

@Composable
fun SocialAboutScreen() = PlaceholderScreen("Sosyal / Hakkımızda")

@Composable
fun PremiumScreen() = PlaceholderScreen("Premium")

@Composable
private fun PlaceholderScreen(title: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { Text(title) }
}
