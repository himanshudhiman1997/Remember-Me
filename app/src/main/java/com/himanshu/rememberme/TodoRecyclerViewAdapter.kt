package com.himanshu.rememberme

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.himanshu.rememberme.databinding.ListItemBinding
import com.himanshu.rememberme.db.Todo

class TodoRecyclerViewAdapter(private val clickListener: (Todo)-> Unit) : RecyclerView.Adapter<MyViewHolder>() {

    private val todos= ArrayList<Todo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.list_item, parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(todos[position], clickListener)
    }

    override fun getItemCount(): Int {
        return todos.size
    }

    fun setList(todoList: List<Todo>){
        todos.clear()
        todos.addAll(todoList)
    }

}

class MyViewHolder(val listItemBinding: ListItemBinding) : RecyclerView.ViewHolder(listItemBinding.root){
    fun bind(todo: Todo, clickListener: (Todo)-> Unit ){
        listItemBinding.titleTextView.text = todo.title
        listItemBinding.descriptionTextView.text = todo.description

        listItemBinding.listItemLayout.setOnClickListener {
            clickListener(todo)
        }
    }
}