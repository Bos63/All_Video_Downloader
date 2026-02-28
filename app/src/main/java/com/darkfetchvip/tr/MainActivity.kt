package com.darkfetchvip.tr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.darkfetchvip.tr.ui.DarkFetchRoot
import com.darkfetchvip.tr.ui.theme.DarkFetchTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            DarkFetchTheme {
                DarkFetchRoot()
            }
        }
    }
}
