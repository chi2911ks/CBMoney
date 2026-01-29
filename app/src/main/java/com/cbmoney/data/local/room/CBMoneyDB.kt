package com.cbmoney.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.cbmoney.data.local.room.dao.BudgetDao
import com.cbmoney.data.local.room.dao.CategoryDao
import com.cbmoney.data.local.room.dao.TransactionDao
import com.cbmoney.data.local.room.entities.BudgetEntity
import com.cbmoney.data.local.room.entities.CategoryEntity
import com.cbmoney.data.local.room.entities.TransactionEntity

@Database(
    entities = [
        CategoryEntity::class,
        BudgetEntity::class,
        TransactionEntity::class
    ], version = 1
)
abstract class CBMoneyDB : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun budgetDao(): BudgetDao
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var INSTANCE: CBMoneyDB? = null

        fun getInstance(context: Context): CBMoneyDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CBMoneyDB::class.java,
                    "cb_database"
                ).addCallback(
                    object : RoomDatabase.Callback() {
                        override fun onCreate(db: SupportSQLiteDatabase) {
                            super.onCreate(db)
                            db.execSQL("PRAGMA foreign_keys=ON")

                        }
                    }
                )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}


