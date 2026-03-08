package com.macaroni10y.androidplayground.domain.usecase

import com.macaroni10y.androidplayground.domain.repository.TodoRepository
import jakarta.inject.Inject

class SyncTodos @Inject constructor(private val repository: TodoRepository) {
    suspend operator fun invoke() = repository.sync()
}
