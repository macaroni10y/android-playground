package com.macaroni10y.androidplayground.domain.model

data class Todo(
    val id: String,
    val title: String,
    val isDone: Boolean,
    val updatedAtEpochMillis: Long,
)
