package com.somnwal.app.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.somnwal.app.core.room.entity.Asset

@Dao
interface AssetDao {
    @Query("SELECT * FROM assets")
    fun getAllAssets(): List<Asset>

    @Insert
    fun insertAsset(asset: Asset)

    @Update
    fun updateAsset(asset: Asset)
}