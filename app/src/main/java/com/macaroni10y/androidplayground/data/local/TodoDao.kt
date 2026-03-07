package com.macaroni10y.androidplayground.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos ORDER BY updatedAtEpochMillis DESC")
    fun observeAll(): Flow<List<TodoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<TodoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(item: TodoEntity)

    @Query("DELETE FROM todos WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM todos WHERE id = :id LIMIT 1")
    suspend fun findById(id: String): TodoEntity?

}
