package com.somnwal.app.core.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.somnwal.app.core.datastore.Preferences
import com.somnwal.app.core.datastore.util.PreferencesSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    private const val DATASTORE_FILENAME = "preferences.pb"
    private val Context.dataStore : DataStore<Preferences> by dataStore(
        fileName = DATASTORE_FILENAME,
        serializer = PreferencesSerializer
    )

    @Provides
    @Singleton
    fun provideDataStore(
        @ApplicationContext context: Context,
    ): DataStore<Preferences> = context.dataStore
}