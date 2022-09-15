package com.example.studentmanakotlin.Model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.io.Serializable

class Task() :RealmObject, Parcelable{
    @PrimaryKey var taskID:String = ""
    var taskName:String = ""
    var time:Int=0
    var personJoin : RealmList<JoinPerson>? = null
    var works:RealmList<Work>?=null
    var isInprogress:Boolean= false
    var process:Int = 0

    constructor(parcel: Parcel) : this() {
        taskID = parcel.readString().toString()
        taskName = parcel.readString().toString()
        time = parcel.readInt()
        isInprogress = parcel.readByte() != 0.toByte()
        process = parcel.readInt()

    }


    fun calcuProcess():Int{

        var pecent = (100/this.works!!.size).toInt()
        var countIsCheck = 0

        for(i in 0 until works!!.size){
            if(works!![i].isCheck) countIsCheck++
        }

        return pecent*countIsCheck
    }


    override fun describeContents(): Int {
        return 0
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(taskID)
        p0?.writeString(taskName)
        p0?.writeInt(time)
        p0?.writeList(personJoin)
        p0?.writeList(works)
        p0?.writeBoolean(isInprogress)

    }

    override fun equals(other: Any?): Boolean {
        val otherTask:Task = other as Task
        return this.taskID.equals(otherTask.taskID) && this.taskName.equals(otherTask.taskName)
                && this.time == otherTask.time
    }

    override fun hashCode(): Int {
        var result = taskID.hashCode()
        result = 31 * result + taskName.hashCode()
        result = 31 * result + time
        result = 31 * result + (personJoin?.hashCode() ?: 0)
        result = 31 * result + (works?.hashCode() ?: 0)
        result = 31 * result + isInprogress.hashCode()
        result = 31 * result + process
        return result
    }


    companion object CREATOR : Parcelable.Creator<Task> {
        override fun createFromParcel(parcel: Parcel): Task {
            return Task(parcel)
        }

        override fun newArray(size: Int): Array<Task?> {
            return arrayOfNulls(size)
        }
    }
}