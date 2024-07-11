package com.somnwal.app.feature.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.somnwal.app.core.designsystem.theme.AppTheme
import com.somnwal.app.feature.main.ui.navigation.MainNavigator
import com.somnwal.app.feature.main.ui.navigation.rememberMainNavigator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val isDarkTheme by mainViewModel.isDarkTheme.collectAsStateWithLifecycle(initialValue = false, this)
            val navigator: MainNavigator = rememberMainNavigator()

            AppTheme(darkTheme = isDarkTheme) {
                MainScreen(
                    navigator = navigator,
                    isDarkTheme = isDarkTheme,
                    onChangeTheme = mainViewModel::updateIsDarkTheme
                )
            }
        }
    }
}