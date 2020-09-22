package com.himanshu.rememberme.db

class TodoRepository(private val dao: TodoDAO) {

    val todos = dao.getAllTodo()

    suspend fun insert(todo: Todo)
    {
        dao.insertTodo(todo)
    }

    suspend fun update(todo: Todo)
    {
        dao.updateTodo(todo)
    }

    suspend fun delete(todo: Todo)
    {
        dao.deleteTodo(todo)
    }

    suspend fun deleteAllTodo()
    {
        dao.deleteAll()
    }
}