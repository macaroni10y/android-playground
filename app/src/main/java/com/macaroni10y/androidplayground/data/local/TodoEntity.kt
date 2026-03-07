package com.macaroni10y.androidplayground.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todos")
data class TodoEntity(
    @PrimaryKey val id: String,
    val title: String,
    val isDone: Boolean,
    val updatedAtEpochMillis: Long
)
