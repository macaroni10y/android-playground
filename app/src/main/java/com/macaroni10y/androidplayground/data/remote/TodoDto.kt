package com.macaroni10y.androidplayground.data.remote

data class TodoDto(
    val id: String,
    val title: String,
    val isDone: Boolean,
    val updatedAtEpochMillis: Long
)
