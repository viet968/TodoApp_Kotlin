package com.example.studentmanakotlin.Presenter

import com.example.studentmanakotlin.Model.JoinPerson
import com.example.studentmanakotlin.Model.Task
import com.example.studentmanakotlin.Model.TodayTask
import com.example.studentmanakotlin.Model.Work
import io.realm.kotlin.types.RealmList

interface IPresenter {
    fun open()
    fun writeTodayTask(todayTask: TodayTask)
    fun writeMultiTodayTask(todayTasks: MutableList<TodayTask>)
    fun readTodayTask():MutableList<TodayTask>
    fun updateIsCompleteStateForTodayTask(todayTask: TodayTask)
    fun writeTask(task: Task)
    fun writeMultiTask(tasks:MutableList<Task>)
    fun readTask():MutableList<Task>
    fun readWorksOfTask(task: Task):MutableList<Work>
    fun readJoinPersonOfTask(task: Task):MutableList<JoinPerson>
    fun updateIsCheckedForWork(work: Work)
    fun updateIsInProgressForTask(task: Task)
    fun writeWork(work: Work)
    fun writeJoinPerson(joinPerson: JoinPerson)
    fun writeMultiWork(works: MutableList<Work>)
    fun writeJoinMultiPerson(joinPersons: MutableList<JoinPerson>)
    fun updateTask(task:Task,works: RealmList<Work>):Task
    fun getIsInProgressForTask(task: Task):Boolean
}