package com.cbmoney.data.repository

import android.util.Log
import com.cbmoney.data.local.datasource.TransactionLocalDataSource
import com.cbmoney.data.mapper.toDomain
import com.cbmoney.data.mapper.toEntity
import com.cbmoney.domain.model.Transaction
import com.cbmoney.domain.model.TransactionCategory
import com.cbmoney.domain.repository.TransactionRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import java.util.UUID

class TransactionRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val transactionLocalDataSource: TransactionLocalDataSource
) : TransactionRepository {
    private val TAG = "TransactionRepository"
    private val userId get() = firebaseAuth.currentUser!!.uid
    override suspend fun getTransactionsByDateRange(
        categoryId: String,
        startDate: Long,
        endDate: Long
    ): List<Transaction> {
        return try {
            transactionLocalDataSource.getTransactionsByDateRange(
                userId,
                categoryId,
                startDate,
                endDate
            ).map { it.toDomain() }
        } catch (e: Exception) {
            Log.d(TAG, "getTransactionsByDateRange: $e")
            emptyList()
        }
    }

    override suspend fun upsertTransaction(transaction: Transaction): Result<Boolean> {
        val trans = transaction.copy(
            id = transaction.id.ifEmpty { UUID.randomUUID().toString() },
            userId = userId
        )
        return try {
            transactionLocalDataSource.upsertTransaction(trans.toEntity())
            Result.success(true)
        } catch (e: Exception) {
            Log.d(TAG, "upsertTransaction: $e")
            Result.failure(e)
        }
    }

    override suspend fun deleteTransaction(transaction: Transaction): Result<Boolean> {
        return try {
            transactionLocalDataSource.deleteTransaction(transaction.toEntity())
            Result.success(true)
        } catch (e: Exception) {
            Log.d(TAG, "deleteTransaction: $e")
            Result.failure(e)
        }
    }

    override fun getAllTransactions(): Flow<List<Transaction>> {
        return try {
            transactionLocalDataSource.getAllTransactions(userId).map {
                it.map { transaction ->
                    transaction.toDomain()
                }
            }
        } catch (e: Exception) {
            Log.d(TAG, "getAllTransactions: $e")
            flow { emit(emptyList()) }
        }
    }

    override fun getRecentTransactions(limit: Int): Flow<List<TransactionCategory>> {
        return try {
            transactionLocalDataSource.getRecentTransactions(userId, limit).toDomain()



        } catch (e: Exception) {
            Log.d(TAG, "getRecentTransactions: $e")
            flow { emit(emptyList()) }

        }
    }
}