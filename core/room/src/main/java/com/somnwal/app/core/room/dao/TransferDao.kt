package com.somnwal.app.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.somnwal.app.core.room.entity.Transfer

@Dao
interface TransferDao {
    @Query("SELECT * FROM transfers")
    fun getAllTransfers(): List<Transfer>

    @Insert
    fun insertTransfer(transfer: Transfer)

    @Update
    fun updateTransfer(transfer: Transfer)
}