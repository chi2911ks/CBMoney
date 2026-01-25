package com.cbmoney.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cbmoney.data.local.room.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert
    suspend fun insertTransaction(transaction: TransactionEntity)
    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getTransactionById(id: String): TransactionEntity
    @Query("SELECT * FROM transactions WHERE id = :userId")
    suspend fun getAllTransactions(userId: String): Flow<List<TransactionEntity>>

}