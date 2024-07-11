package com.somnwal.app.core.room.di

import android.content.Context
import androidx.room.Room
import com.somnwal.app.core.room.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, "budget_map.db")
            .build()

    @Singleton
    @Provides
    fun provideAssetDao(appDatabase: AppDatabase) =
        appDatabase.assetDao()

    @Singleton
    @Provides
    fun provideTransferDao(appDatabase: AppDatabase) =
        appDatabase.transferDao()
}