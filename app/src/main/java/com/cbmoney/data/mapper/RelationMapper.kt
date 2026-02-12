package com.cbmoney.data.mapper

import com.cbmoney.data.local.room.relation.BudgetWithCategory
import com.cbmoney.data.local.room.relation.CategoryWithSpending
import com.cbmoney.data.local.room.relation.TotalExpenseAndIncome
import com.cbmoney.data.local.room.relation.TransactionWithCategory
import com.cbmoney.domain.model.BudgetDetails
import com.cbmoney.domain.model.CategorySpending
import com.cbmoney.domain.model.FinancialSummary
import com.cbmoney.domain.model.TransactionDetails

fun BudgetWithCategory.toDomain(): BudgetDetails = BudgetDetails(
    budget = budget.toDomain(),
    categoryName = categoryName,
    categoryIcon = categoryIcon,
    iconColor = iconColor,
)
fun TransactionWithCategory.toDomain(): TransactionDetails {
    return TransactionDetails(
        transaction = transaction.toDomain(),
        categoryName = categoryName,
        categoryIcon = categoryIcon,
        iconColor = iconColor
    )
}
fun CategoryWithSpending.toDomain(): CategorySpending {
    return CategorySpending(
        categoryId = categoryId,
        categoryName = categoryName,
        categoryIcon = categoryIcon,
        iconColor = iconColor,
        type = type,
        countTransaction = countTransaction,
        totalSpending = totalSpending
    )
}
fun TotalExpenseAndIncome.toDomain(): FinancialSummary = FinancialSummary(
    totalExpense = totalExpense,
    totalIncome = totalIncome,
    year = year,
    month = month
)