package com.somnwal.app.core.room.di

import androidx.room.Database
import com.somnwal.app.core.room.entity.Asset
import com.somnwal.app.core.room.entity.Transfer
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
@Database(
    entities = [
        Asset::class,
        Transfer::class,
    ],
    version = 1
)
object AppDatabaseModule {


}