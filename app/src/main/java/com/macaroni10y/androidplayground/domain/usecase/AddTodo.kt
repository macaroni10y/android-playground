package com.macaroni10y.androidplayground.domain.usecase

import com.macaroni10y.androidplayground.domain.repository.TodoRepository
import jakarta.inject.Inject

class AddTodo @Inject constructor(private val repository: TodoRepository) {
    suspend operator fun invoke(title: String) = repository.addTodo(title)
}
