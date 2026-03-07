package com.macaroni10y.androidplayground.di

import android.content.Context
import androidx.room.Room
import com.macaroni10y.androidplayground.data.local.TodoDao
import com.macaroni10y.androidplayground.data.local.TodoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext context: Context): TodoDatabase {
        return Room.databaseBuilder(context, TodoDatabase::class.java, "todo.db").build()
    }

    @Provides
    fun provideTodoDao(db: TodoDatabase): TodoDao = db.todoDao()
}
