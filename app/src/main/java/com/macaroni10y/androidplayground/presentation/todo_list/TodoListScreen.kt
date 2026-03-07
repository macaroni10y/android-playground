package com.macaroni10y.androidplayground.presentation.todo_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    vm: TodoListViewModel,
    onShowToast: (String) -> Unit,
) {
    val state by vm.state.collectAsState()

    LaunchedEffect(Unit) {
        vm.events.collectLatest { e ->
            when (e) {
                is TodoListUiEvent.ShowToast -> onShowToast(e.message)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("TODO") },
                actions = {
                    IconButton(onClick = vm::onRefresh) { Text("Sync") }
                }
            )
        }
    ) { padding ->
        Column(modifier = androidx.compose.ui.Modifier.padding(padding)) {
            if (state.errorMessage != null) {
                Text("Error: ${state.errorMessage}")
            }

            Row {
                TextField(
                    value = state.input,
                    onValueChange = vm::onInputChange,
                    modifier = androidx.compose.ui.Modifier.weight(1f)
                )
                Button(onClick = vm::onAddClick, enabled = !state.isLoading) {
                    Text("Add")
                }
            }

            if (state.isLoading) {
                LinearProgressIndicator()
            }

            state.items.forEach { item ->
                Row {
                    Checkbox(checked = item.isDone, onCheckedChange = { vm.onToggle(item.id) })
                    Text(item.title, modifier = androidx.compose.ui.Modifier.weight(1f))
                    TextButton(onClick = { vm.onDelete(item.id) }) { Text("Delete") }
                }
            }
        }
    }
}
