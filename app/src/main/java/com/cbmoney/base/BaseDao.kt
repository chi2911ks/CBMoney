package com.cbmoney.base

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface BaseDao<T> {
    @Insert
    suspend fun insert(entity: T): Long

    @Insert
    suspend fun insert(entities: List<T>)

    @Update
    suspend fun update(entity: T): Int

    @Update
    suspend fun update(entities: List<T>): Int

    @Delete
    suspend fun delete(entity: T)
    @Delete
    suspend fun delete(entities: List<T>)
}
