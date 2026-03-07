package com.macaroni10y.androidplayground.data.remote

import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface TodoApi {
    @GET("/todos")
    suspend fun getTodos(): List<TodoDto>
    @POST("/todos")
    suspend fun create(@Body createTodoRequest: CreateTodoRequest): TodoDto
    @PATCH("/todos/{id}")
    suspend fun patch(@Path("id") id: String, @Body patchTodoRequest: PatchTodoRequest): TodoDto
    @DELETE("/todos/{id}")
    suspend fun delete(@Path("id") id: String)
}

data class CreateTodoRequest(val title: String, val isDone: Boolean = false, val updatedAtEpochMillis: Long = System.currentTimeMillis())
data class PatchTodoRequest(val isDone: Boolean)
