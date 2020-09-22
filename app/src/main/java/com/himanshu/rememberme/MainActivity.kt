package com.himanshu.rememberme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.himanshu.rememberme.databinding.ActivityMainBinding
import com.himanshu.rememberme.db.TodoDatabase
import com.himanshu.rememberme.db.TodoRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var todoViewModel: TodoViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = TodoDatabase.getInstance(application).todoDAO

        val repository = TodoRepository(dao)
        val factory = TodoViewModelFactory(repository)

        todoViewModel = ViewModelProvider(this, factory).get(TodoViewModel::class.java)
        binding.myViewModel= todoViewModel

        binding.lifecycleOwner = this

        displayTodoList()
    }

    private fun displayTodoList(){
        todoViewModel.todos.observe(this, Observer {
            Log.i("Mytag", it.toString())
        })
    }
}