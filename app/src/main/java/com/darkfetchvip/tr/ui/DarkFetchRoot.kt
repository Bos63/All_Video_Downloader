package com.darkfetchvip.tr.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Badge
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.darkfetchvip.tr.ui.screens.DownloadsScreen
import com.darkfetchvip.tr.ui.screens.HomeScreen
import com.darkfetchvip.tr.ui.screens.SettingsScreen
import com.darkfetchvip.tr.ui.screens.ToolsScreen

data class DrawerGroup(val title: String, val items: List<String>)

private data class BottomNavItem(val route: String, val label: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)

@Composable
fun DarkFetchRoot() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val bottomItems = listOf(
        BottomNavItem("home", "Home", Icons.Default.Home),
        BottomNavItem("downloads", "Downloads", Icons.Default.Download),
        BottomNavItem("tools", "Tools", Icons.Default.Build),
        BottomNavItem("settings", "Settings", Icons.Default.Settings)
    )
    val drawerGroups = listOf(
        DrawerGroup("Hızlı Erişim", listOf("🏠 Ana Sayfa", "⚡ Hızlı İndir", "🧭 Tarayıcı", "📥 İndirilenler")),
        DrawerGroup("İndirme", listOf("🎬 Video İndir", "🎵 Müzik İndir", "🎧 Playlist/Toplu İş", "🗂 Kuyruk Yöneticisi")),
        DrawerGroup("Araçlar", listOf("🧪 Format Dönüştürücü", "✂️ Video Kes / Trim", "🏷 Metadata", "🧹 Temizlik / Cache")),
        DrawerGroup("Uygulama", listOf("⚙️ Ayarlar", "💎 Premium", "❓ Yardım / SSS", "🛡 Gizlilik & Kullanım", "⭐ Uygulamayı Puanla"))
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(modifier = Modifier.background(MaterialTheme.colorScheme.surface)) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("DarkFetch", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Badge(modifier = Modifier.padding(top = 6.dp)) { Text("VIP") }
                }
                LazyColumn(modifier = Modifier.fillMaxSize().padding(horizontal = 12.dp)) {
                    drawerGroups.forEach { group ->
                        item {
                            Text(group.title, style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(vertical = 10.dp))
                        }
                        items(group.items) { item ->
                            Text(item, modifier = Modifier.fillMaxWidth().clickable { }.padding(10.dp))
                        }
                    }
                    item { Spacer(Modifier.height(24.dp)); Text("Version 1.0.0", modifier = Modifier.padding(10.dp)) }
                }
            }
        }
    ) {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    bottomItems.forEach { item ->
                        val selected = currentDestination?.hierarchy?.any { it.route == item.route } == true
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home") { HomeScreen() }
                composable("downloads") { DownloadsScreen() }
                composable("tools") { ToolsScreen() }
                composable("settings") { SettingsScreen() }
            }
        }
    }
}
