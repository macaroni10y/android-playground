package com.macaroni10y.androidplayground.domain.usecase

import com.macaroni10y.androidplayground.domain.repository.TodoRepository
import jakarta.inject.Inject

class ObserveTodos @Inject constructor(private val repository: TodoRepository) {
    operator fun invoke() = repository.observeTodos()
}
