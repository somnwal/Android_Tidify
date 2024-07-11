package com.somnwal.app.core.datastore.datasource

import android.service.autofill.UserData
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import com.somnwal.app.core.datastore.Preferences
import com.somnwal.app.core.datastore.copy
import com.somnwal.app.data.model.search.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesDataSource @Inject constructor(
    private val preferences: DataStore<Preferences>
) {
    val prefs = preferences.data
        .map {
            UserData(
                isDarkTheme = it.isDarkTheme
            )
        }

    suspend fun updateIsDarkTheme(isDarkTheme: Boolean) {
        try {
            preferences.updateData {
                it.copy {
                    this.isDarkTheme = isDarkTheme
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}