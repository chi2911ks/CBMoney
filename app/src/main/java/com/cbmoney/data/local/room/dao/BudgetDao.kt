package com.cbmoney.data.local.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.cbmoney.data.local.room.entities.BudgetEntity

@Dao
interface BudgetDao {
    @Insert
    suspend fun insertBudget(budget: BudgetEntity)
    @Query("SELECT * FROM budgets WHERE userId = :userId")
    suspend fun getAllBudgets(userId: String): List<BudgetEntity>
    @Query("SELECT * FROM budgets WHERE id = :id")
    suspend fun getBudgetById(id: String): BudgetEntity

}