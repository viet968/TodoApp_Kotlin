package com.example.studentmanakotlin.ViewModel_LiveData

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.studentmanakotlin.Model.Task

class TaskViewModel : ViewModel(){
    var mTasks : MutableLiveData<MutableList<Task>> = MutableLiveData()

    fun setNewTasks(newTasks:MutableList<Task>){
        mTasks.value = newTasks
    }
}