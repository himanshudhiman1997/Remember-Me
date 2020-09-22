package com.himanshu.rememberme

import androidx.databinding.Bindable
import androidx.databinding.Observable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.himanshu.rememberme.db.Event
import com.himanshu.rememberme.db.Todo
import com.himanshu.rememberme.db.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel(), Observable {


    val todos = repository.todos
    private var isUpdateOrDelete = false
    private lateinit var todoToUpdateOrDelete: Todo

    @Bindable
    val inputTitle = MutableLiveData<String>()

    @Bindable
    val inputDescription = MutableLiveData<String>()

    @Bindable
    val saveOrUpdateButtonText = MutableLiveData<String>()

    @Bindable
    val clearAllOrDeleteButtonText = MutableLiveData<String>()

    private val statusMessage = MutableLiveData<Event<String>>()

    val message: LiveData<Event<String>>
        get() = statusMessage

    init {
        saveOrUpdateButtonText.value = "Save"
        clearAllOrDeleteButtonText.value = "Clear All"

    }

    fun saveOrUpdate() {

        if (inputTitle.value == null || inputTitle.value == "") {
            statusMessage.value = Event("Enter the title")
        } else if (inputDescription.value == null || inputDescription.value == "") {
            statusMessage.value = Event("Enter the description")
        } else {
            if (isUpdateOrDelete) {
                todoToUpdateOrDelete.title = inputTitle.value!!
                todoToUpdateOrDelete.description = inputDescription.value!!
                update(todoToUpdateOrDelete)
            } else {
                val title = inputTitle.value!!
                val description = inputDescription.value!!

                insert(Todo(0, title, description))
                inputTitle.value = null
                inputDescription.value = null
            }
        }


    }

    fun clearAllOrDelete() {
        if (isUpdateOrDelete) {
            delete(todoToUpdateOrDelete)
        } else {
            clearAll()
        }
    }

    fun insert(todo: Todo) {
        viewModelScope.launch {
            val newRowId = repository.insert(todo)
            if (newRowId > -1) {
                statusMessage.value = Event("Todo inserted successfully")
            } else {
                statusMessage.value = Event("Error occurred")
            }
        }
    }

    fun update(todo: Todo) {
        viewModelScope.launch {
            val noOfRows = repository.update(todo)
            if (noOfRows > 0) {
                inputTitle.value = null
                inputDescription.value = null

                isUpdateOrDelete = false
                saveOrUpdateButtonText.value = "Save"
                clearAllOrDeleteButtonText.value = "Clear All"

                statusMessage.value = Event("Todo updated successfully")
            } else {
                statusMessage.value = Event("Error occurred")
            }
        }
    }

    fun delete(todo: Todo) {
        viewModelScope.launch {
            val noOfRowsDeleted = repository.delete(todo)
            if (noOfRowsDeleted > 0) {
                inputTitle.value = null
                inputDescription.value = null

                isUpdateOrDelete = false
                saveOrUpdateButtonText.value = "Save"
                clearAllOrDeleteButtonText.value = "Clear All"

                statusMessage.value = Event("Todo deleted successfully")
            } else {
                statusMessage.value = Event("Error occurred")
            }

        }
    }

    fun initUpdateAndDelete(todo: Todo) {
        inputTitle.value = todo.title
        inputDescription.value = todo.description

        isUpdateOrDelete = true
        todoToUpdateOrDelete = todo
        saveOrUpdateButtonText.value = "Update"
        clearAllOrDeleteButtonText.value = "Delete"
    }

    fun clearAll() {
        viewModelScope.launch {
            val noOfRowsDeleted = repository.deleteAllTodo()
            if (noOfRowsDeleted > 0) {
                statusMessage.value = Event("All Todos deleted successfully")
            } else {
                statusMessage.value = Event("Error occurred")
            }

        }
    }

    override fun addOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

    override fun removeOnPropertyChangedCallback(callback: Observable.OnPropertyChangedCallback?) {

    }

}