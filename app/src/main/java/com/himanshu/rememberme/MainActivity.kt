package com.himanshu.rememberme

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.himanshu.rememberme.databinding.ActivityMainBinding
import com.himanshu.rememberme.db.Todo
import com.himanshu.rememberme.db.TodoDatabase
import com.himanshu.rememberme.db.TodoRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var todoViewModel: TodoViewModel
    private lateinit var adapter: TodoRecyclerViewAdapter

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

        initRecyclerView()

        todoViewModel.message.observe(this, Observer {
            it.getContentIfNotHandled()?.let {
                Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initRecyclerView() {
        binding.todoRecyclerView.layoutManager = LinearLayoutManager(this)
        adapter = TodoRecyclerViewAdapter { selectedItem: Todo -> listItemClicked(selectedItem) }
        binding.todoRecyclerView.adapter = adapter
        displayTodoList()
    }

    private fun displayTodoList(){
        todoViewModel.todos.observe(this, Observer {
            adapter.setList(it)
            adapter.notifyDataSetChanged()
        })
    }

    private fun listItemClicked(todo: Todo){
        todoViewModel.initUpdateAndDelete(todo)
    }
}