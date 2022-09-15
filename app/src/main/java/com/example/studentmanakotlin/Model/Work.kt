package com.example.studentmanakotlin.Model

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.io.Serializable

class Work():RealmObject, Parcelable {
    @PrimaryKey var workID:String = ""
    var taskID:String = ""
    var workName:String= ""
    var isCheck:Boolean = false

    constructor(parcel: Parcel) : this() {
        workID = parcel.readString().toString()
        taskID = parcel.readString().toString()
        workName = parcel.readString().toString()
        isCheck = parcel.readByte() != 0.toByte()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(workID)
        parcel.writeString(taskID)
        parcel.writeString(workName)
        parcel.writeBoolean(isCheck)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return workName+"\t"+isCheck.toString()
    }

    companion object CREATOR : Parcelable.Creator<Work> {
        override fun createFromParcel(parcel: Parcel): Work {
            return Work(parcel)
        }

        override fun newArray(size: Int): Array<Work?> {
            return arrayOfNulls(size)
        }
    }
}
