package com.darkfetchvip.tr.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.DrawerValue
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.darkfetchvip.tr.ui.screens.DownloadsScreen
import com.darkfetchvip.tr.ui.screens.HomeScreen
import com.darkfetchvip.tr.ui.screens.SettingsScreen
import com.darkfetchvip.tr.ui.screens.ToolsScreen

@Composable
fun DarkFetchRoot() {
    val nav = rememberNavController()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val drawerItems = mapOf(
        "Hızlı Erişim" to listOf("Ana Sayfa", "Hızlı İndir", "İndirilenler", "Kuyruk"),
        "İndirme" to listOf("Video İndir", "Müzik İndir", "Toplu İndirme", "Format Seçici"),
        "Araçlar" to listOf("Format Dönüştürücü", "Video Kes", "Metadata", "Depolama Analizi", "Cache Temizle"),
        "Uygulama" to listOf("Ayarlar", "Premium", "Yardım", "Gizlilik", "Uygulamayı Puanla")
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(Modifier.padding(16.dp)) {
                    Text("DarkFetch VIP", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    Spacer(Modifier.height(12.dp))
                }
                LazyColumn(Modifier.fillMaxSize().padding(horizontal = 12.dp)) {
                    drawerItems.forEach { (header, items) ->
                        item { Text(header, modifier = Modifier.padding(top = 8.dp, bottom = 4.dp), fontWeight = FontWeight.SemiBold) }
                        items(items) { label -> Text(label, modifier = Modifier.fillMaxWidth().clickable {}.padding(10.dp)) }
                    }
                    item { Text("v1.0.0 • build 1", modifier = Modifier.padding(12.dp)) }
                }
            }
        }
    ) {
        Scaffold(bottomBar = {
            val entry by nav.currentBackStackEntryAsState()
            val route = entry?.destination?.route
            NavigationBar {
                NavigationBarItem(selected = route == "home", onClick = { nav.navigate("home") { popUpTo(nav.graph.findStartDestination().id) } }, icon = { androidx.compose.material3.Icon(Icons.Default.Home, null) }, label = { Text("Home") })
                NavigationBarItem(selected = route == "downloads", onClick = { nav.navigate("downloads") { popUpTo(nav.graph.findStartDestination().id) } }, icon = { androidx.compose.material3.Icon(Icons.Default.Download, null) }, label = { Text("Downloads") })
                NavigationBarItem(selected = route == "tools", onClick = { nav.navigate("tools") { popUpTo(nav.graph.findStartDestination().id) } }, icon = { androidx.compose.material3.Icon(Icons.Default.Build, null) }, label = { Text("Tools") })
                NavigationBarItem(selected = route == "settings", onClick = { nav.navigate("settings") { popUpTo(nav.graph.findStartDestination().id) } }, icon = { androidx.compose.material3.Icon(Icons.Default.Settings, null) }, label = { Text("Settings") })
            }
        }) { inner ->
            NavHost(navController = nav, startDestination = "home", modifier = Modifier.padding(inner)) {
                composable("home") { HomeScreen() }
                composable("downloads") { DownloadsScreen() }
                composable("tools") { ToolsScreen() }
                composable("settings") { SettingsScreen() }
            }
        }
    }
}
