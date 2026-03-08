package com.macaroni10y.androidplayground.data.repository

import com.macaroni10y.androidplayground.data.local.TodoDao
import com.macaroni10y.androidplayground.data.mapper.toDomain
import com.macaroni10y.androidplayground.data.mapper.toEntity
import com.macaroni10y.androidplayground.data.remote.CreateTodoRequest
import com.macaroni10y.androidplayground.data.remote.PatchTodoRequest
import com.macaroni10y.androidplayground.data.remote.TodoApi
import com.macaroni10y.androidplayground.domain.model.Todo
import com.macaroni10y.androidplayground.domain.repository.TodoRepository
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@Singleton
class TodoRepositoryImpl @Inject constructor(
    private val api: TodoApi,
    private val dao: TodoDao,
) : TodoRepository {
    override fun observeTodos(): Flow<List<Todo>> =
        dao.observeAll().map { list -> list.map { it.toDomain() } }

    override suspend fun addTodo(title: String): Todo {
        val created = api.create(CreateTodoRequest(title))
        dao.upsert(created.toEntity())
        return created.toDomain()
    }

    override suspend fun toggleDone(id: String): Todo {
        val local = dao.findById(id) ?: error("Not found: $id")
        val patched = api.patch(id, PatchTodoRequest(isDone = !local.isDone))
        dao.upsert(patched.toEntity())
        return patched.toDomain()
    }

    override suspend fun delete(id: String) {
        api.delete(id)
        dao.delete(id)
    }

    override suspend fun sync() {
        val remote = api.getTodos()
        dao.upsertAll(remote.map { it.toEntity() })
    }
}
