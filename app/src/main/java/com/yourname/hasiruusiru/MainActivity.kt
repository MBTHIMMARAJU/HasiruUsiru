package com.yourname.hasiruusiru

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.*
import com.yourname.hasiruusiru.ui.theme.HasiruUsiruTheme

class MainActivity : ComponentActivity() {
    private val viewModel: TreeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HasiruUsiruTheme {
                var currentScreen by remember { mutableStateOf<AppScreen>(AppScreen.Map) }

                when (currentScreen) {
                    AppScreen.Map -> MapScreen(
                        viewModel = viewModel,
                        onAddTreeClick = { currentScreen = AppScreen.Tagger },
                        onNavigateToGuide = { currentScreen = AppScreen.Guide },
                        onNavigateToDashboard = { currentScreen = AppScreen.Dashboard }
                    )
                    AppScreen.Tagger -> TreeTaggerScreen(
                        viewModel = viewModel,
                        onBack = { currentScreen = AppScreen.Map }
                    )
                    AppScreen.Guide -> SpeciesGuideScreen(
                        viewModel = viewModel,
                        onBack = { currentScreen = AppScreen.Map }
                    )
                    AppScreen.Dashboard -> DashboardScreen(
                        viewModel = viewModel,
                        onBack = { currentScreen = AppScreen.Map }
                    )
                }
            }
        }
    }
}

sealed class AppScreen {
    object Map : AppScreen()
    object Tagger : AppScreen()
    object Guide : AppScreen()
    object Dashboard : AppScreen()
}
