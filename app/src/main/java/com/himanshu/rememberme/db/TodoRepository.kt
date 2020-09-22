package com.himanshu.rememberme.db

class TodoRepository(private val dao: TodoDAO) {

    val todos = dao.getAllTodo()

    suspend fun insert(todo: Todo) : Long
    {
        return dao.insertTodo(todo)
    }

    suspend fun update(todo: Todo) : Int
    {
        return dao.updateTodo(todo)
    }

    suspend fun delete(todo: Todo) : Int
    {
        return dao.deleteTodo(todo)
    }

    suspend fun deleteAllTodo() : Int
    {
        return dao.deleteAll()
    }
}