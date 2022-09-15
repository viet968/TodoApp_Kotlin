package com.example.studentmanakotlin.Presenter

import com.example.studentmanakotlin.Model.JoinPerson
import com.example.studentmanakotlin.Model.Task
import com.example.studentmanakotlin.Model.TodayTask
import com.example.studentmanakotlin.Model.Work
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.ext.query
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.query.RealmResults
import io.realm.kotlin.types.RealmList

class PresenterImp : IPresenter{
    private lateinit var realm: Realm

    override fun open() {
        val configuration = RealmConfiguration.create(schema = setOf(TodayTask::class,Task::class,Work::class,
        JoinPerson::class))
        realm = Realm.open(configuration)
    }

     override  fun writeTodayTask(todayTask: TodayTask) {
        realm.writeBlocking { copyToRealm(todayTask) }
    }

    override  fun writeMultiTodayTask(todayTasks: MutableList<TodayTask>) {
        for (i in 0 until todayTasks.size){
            writeTodayTask(todayTasks[i])
        }
    }

    override fun readTodayTask(): MutableList<TodayTask> {
        return realm.query<TodayTask>().find().toMutableList()
    }

    override  fun updateIsCompleteStateForTodayTask(todayTask: TodayTask) {
        val findTodayTask = realm.query<TodayTask>("todayTaskId == $0",todayTask.todayTaskId).find()
        realm.writeBlocking {
            findLatest(findTodayTask[0])?.isComplete= !todayTask.isComplete
        }

    }

    override fun writeTask(task: Task) {
        realm.writeBlocking { copyToRealm(task) }
    }

    override fun writeMultiTask(tasks: MutableList<Task>) {
        for (i in 0 until tasks.size){
            writeTask(tasks[i])
        }
    }



    override fun readTask(): MutableList<Task> {
        return realm.query<Task>().find().toMutableList()
    }

    override fun readWorksOfTask(task: Task) : MutableList<Work>{

        val findTask:RealmResults<Task> = realm.query<Task>("taskID == $0",task.taskID).find()
        val getTask = findTask[0]
        return getTask.works!!.toMutableList()
    }

    override fun readJoinPersonOfTask(task: Task): MutableList<JoinPerson> {
        val findTask:RealmResults<Task> = realm.query<Task>("taskID == $0",task.taskID).find()
        val getTask = findTask[0]
        return getTask.personJoin!!.toMutableList()
    }

    override fun updateIsCheckedForWork(work: Work) {
        val findWork:RealmResults<Work> = realm.query<Work>("workID==$0 ",work.workID).find()
        realm.writeBlocking {
            findLatest(findWork[0])?.isCheck= !work.isCheck
        }
    }

    override fun updateIsInProgressForTask(task: Task) {

    }

    override fun writeWork(work: Work) {
        realm.writeBlocking { copyToRealm(work) }
    }

    override fun writeJoinPerson(joinPerson: JoinPerson) {
        realm.writeBlocking { copyToRealm(joinPerson) }
    }

    override fun writeMultiWork(works: MutableList<Work>) {
        for(i in 0 until works.size){
            writeWork(works[i])
        }
    }

    override fun writeJoinMultiPerson(joinPersons: MutableList<JoinPerson>) {
        for(i in 0 until joinPersons.size){
            writeJoinPerson(joinPersons[i])
        }
    }


    override fun updateTask(task: Task, works: RealmList<Work>):Task {
        val findTask:RealmResults<Task> = realm.query<Task>("taskID == $0",task.taskID).find()
//        realm.writeBlocking {
//            findLatest(findTask[0])?.works = works
//        }
        return findTask[0]
    }

    override fun getIsInProgressForTask(task: Task): Boolean {
        val findTask:RealmResults<Task> = realm.query<Task>("taskID == $0",task.taskID).find()
        return findTask[0].isInprogress
    }

}