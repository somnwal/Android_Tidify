package com.somnwal.app.feature.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
//    private val settingsRepository: SettingsRepository
) : ViewModel() {
    val isDarkTheme = flowOf(true)

    fun updateIsDarkTheme(isDarkTheme: Boolean) =
        viewModelScope.launch {
//            settingsRepository.updateIsDarkTheme(isDarkTheme)
        }
}