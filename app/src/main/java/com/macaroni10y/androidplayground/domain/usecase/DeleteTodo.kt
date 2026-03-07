package com.macaroni10y.androidplayground.domain.usecase

import com.macaroni10y.androidplayground.domain.repository.TodoRepository
import jakarta.inject.Inject

class DeleteTodo @Inject constructor(private val repository: TodoRepository) {
    suspend operator fun invoke(id: String) = repository.delete(id)
}
