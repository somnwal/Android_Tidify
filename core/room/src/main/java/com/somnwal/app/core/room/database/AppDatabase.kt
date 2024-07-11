package com.somnwal.app.core.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.somnwal.app.core.room.dao.AssetDao
import com.somnwal.app.core.room.dao.TransferDao
import com.somnwal.app.core.room.entity.Asset
import com.somnwal.app.core.room.entity.Transfer

@Database(
    entities = [
        Asset::class,
        Transfer::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun assetDao(): AssetDao
    abstract fun transferDao(): TransferDao



}