package com.himanshu.rememberme

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himanshu.rememberme.db.Todo
import com.himanshu.rememberme.db.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel(), Observable {


    val todos = repository.todos

    @Bindable
    val inputTitle = MutableLiveData<String>()

    @Bindable
    val inputDescription = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"

    }

    fun saveOrUpdate() {
        val title = inputTitle.value!!
        val description = inputDescription.value!!

        insert(Todo(0, title, description))
        inputTitle.value = null
        inputDescription.value = null
    }

    fun clearAllOrDelete() {
        clearAll()
    }

    fun insert(todo: Todo)
    {
        viewModelScope.launch {
            repository.insert(todo)
        }
    }

    fun update(todo: Todo)
    {
        viewModelScope.launch {
            repository.update(todo)
        }
    }

    fun delete(todo: Todo)
    {
        viewModelScope.launch {
            repository.delete(todo)
        }
    }

    fun clearAll()
    {
        viewModelScope.launch {
            repository.deleteAllTodo()
        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}