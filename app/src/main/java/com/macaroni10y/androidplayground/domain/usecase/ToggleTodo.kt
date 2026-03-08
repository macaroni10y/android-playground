package com.macaroni10y.androidplayground.domain.usecase

import com.macaroni10y.androidplayground.domain.repository.TodoRepository
import jakarta.inject.Inject

class ToggleTodo @Inject constructor(private val repository: TodoRepository) {
    suspend operator fun invoke(id: String) = repository.toggleDone(id)
}
