package com.himanshu.rememberme.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_data_table")
data class Todo(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "todo_id")
    var id: Int,

    @ColumnInfo(name = "todo_title")
    var title: String,

    @ColumnInfo(name = "todo_description")
    var description: String
)