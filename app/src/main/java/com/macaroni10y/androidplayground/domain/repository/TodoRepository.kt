package com.macaroni10y.androidplayground.domain.repository

import com.macaroni10y.androidplayground.domain.model.Todo
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun observeTodos(): Flow<List<Todo>>
    suspend fun addTodo(title: String): Todo
    suspend fun toggleDone(id: String): Todo
    suspend fun delete(id: String)
    suspend fun sync(): Unit
}
