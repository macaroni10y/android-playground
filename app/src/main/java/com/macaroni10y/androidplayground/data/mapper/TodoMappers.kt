package com.macaroni10y.androidplayground.data.mapper

import com.macaroni10y.androidplayground.data.local.TodoEntity
import com.macaroni10y.androidplayground.data.remote.TodoDto
import com.macaroni10y.androidplayground.domain.model.Todo

fun TodoDto.toDomain() = Todo(id, title, isDone, updatedAtEpochMillis)
fun TodoEntity.toDomain() = Todo(id, title, isDone, updatedAtEpochMillis)

fun TodoDto.toEntity() = TodoEntity(id, title, isDone, updatedAtEpochMillis)
fun Todo.toEntity() = TodoEntity(id, title, isDone, updatedAtEpochMillis)
