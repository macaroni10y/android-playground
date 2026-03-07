package com.macaroni10y.androidplayground.presentation.todo_list

sealed interface TodoListUiEvent {
    data class ShowToast(val message: String) : TodoListUiEvent
}
