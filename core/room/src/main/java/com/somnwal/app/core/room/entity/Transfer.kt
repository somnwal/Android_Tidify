package com.somnwal.app.core.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transfers")
data class Transfer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "from_asset_id") val fromAssetId: Int,
    @ColumnInfo(name = "to_asset_id") val toAssetId: Int,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "memo") val memo: String
)