package com.himanshu.rememberme.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TodoDAO{

    @Insert
    suspend fun insertTodo(todo: Todo) : Long

    @Update
    suspend fun updateTodo(todo: Todo) : Int

    @Delete
    suspend fun deleteTodo(todo: Todo ) : Int

    @Query("DELETE FROM todo_data_table")
    suspend fun deleteAll() : Int

    @Query("SELECT * FROM todo_data_table")
    fun getAllTodo() : LiveData<List<Todo>>

}