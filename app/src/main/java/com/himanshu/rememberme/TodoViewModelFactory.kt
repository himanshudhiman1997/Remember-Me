package com.himanshu.rememberme

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.himanshu.rememberme.db.TodoRepository
import java.lang.IllegalArgumentException

class TodoViewModelFactory(private val repository: TodoRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java))
        {
            return TodoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}