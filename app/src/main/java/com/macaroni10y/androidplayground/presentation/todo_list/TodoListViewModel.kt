package com.macaroni10y.androidplayground.presentation.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.macaroni10y.androidplayground.domain.usecase.AddTodo
import com.macaroni10y.androidplayground.domain.usecase.DeleteTodo
import com.macaroni10y.androidplayground.domain.usecase.ObserveTodos
import com.macaroni10y.androidplayground.domain.usecase.SyncTodos
import com.macaroni10y.androidplayground.domain.usecase.ToggleTodo
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.onFailure

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val observeTodos: ObserveTodos,
    private val addTodo: AddTodo,
    private val toggleTodo: ToggleTodo,
    private val deleteTodo: DeleteTodo,
    private val syncTodos: SyncTodos,
) : ViewModel() {

    private val _state = MutableStateFlow(TodoListUiState(isLoading = true))
    val state: StateFlow<TodoListUiState> = _state.asStateFlow()

    private val _events = MutableSharedFlow<TodoListUiEvent>()
    val events: SharedFlow<TodoListUiEvent> = _events.asSharedFlow()

    init {
        viewModelScope.launch {
            // ローカル購読（Single Source of Truth）
            observeTodos().collect { todos ->
                _state.update {
                    it.copy(
                        isLoading = false,
                        items = todos.map { t -> TodoItemUi(t.id, t.title, t.isDone) },
                        errorMessage = null
                    )
                }
            }
        }

        viewModelScope.launch {
            // 起動時同期（任意）
            runCatching { syncTodos() }
                .onFailure { _state.update { s -> s.copy(errorMessage = it.message) } }
        }
    }

    fun onInputChange(value: String) {
        _state.update { it.copy(input = value) }
    }

    fun onAddClick() {
        val title = state.value.input
        if (title.isBlank()) return

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            runCatching { addTodo(title) }
                .onSuccess {
                    _state.update { it.copy(isLoading = false, input = "") }
                }
                .onFailure {
                    _state.update { it.copy(isLoading = false, errorMessage = it.errorMessage) }
                    _events.emit(TodoListUiEvent.ShowToast("追加に失敗: ${it.message}"))
                }
        }
    }

    fun onToggle(id: String) {
        viewModelScope.launch {
            runCatching { toggleTodo(id) }
                .onFailure { _events.emit(TodoListUiEvent.ShowToast("更新に失敗: ${it.message}")) }
        }
    }

    fun onDelete(id: String) {
        viewModelScope.launch {
            runCatching { deleteTodo(id) }
                .onFailure { _events.emit(TodoListUiEvent.ShowToast("削除に失敗: ${it.message}")) }
        }
    }

    fun onRefresh() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            runCatching { syncTodos() }
                .onSuccess { _state.update { it.copy(isLoading = false) } }
                .onFailure { _state.update { it.copy(isLoading = false, errorMessage = it.errorMessage) } }
        }
    }
}
