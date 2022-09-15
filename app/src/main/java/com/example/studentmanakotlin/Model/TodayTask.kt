package com.example.studentmanakotlin.Model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey

class TodayTask : RealmObject{
    @PrimaryKey var todayTaskId:String = ""
     var taskName:String = ""
     var time:String = ""
    var isComplete:Boolean = false
}