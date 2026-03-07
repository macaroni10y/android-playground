package com.macaroni10y.androidplayground.presentation.todo_list

data class TodoListUiState(
    val isLoading: Boolean = false,
    val items: List<TodoItemUi> = emptyList(),
    val input: String = "",
    val errorMessage: String? = null,
)

data class TodoItemUi(
    val id: String,
    val title: String,
    val isDone: Boolean,
)
