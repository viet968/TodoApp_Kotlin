package com.example.studentmanakotlin.Model

import android.os.Parcel
import android.os.Parcelable
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.io.Serializable

class JoinPerson( ) : RealmObject, Parcelable {
    @PrimaryKey var personID:String = ""
    var taskID:String = ""
    var name:String = ""
    var image:Int = 0

    constructor(parcel: Parcel) : this() {
        personID = parcel.readString().toString()
        taskID = parcel.readString().toString()
        name = parcel.readString().toString()
        image = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(personID)
        parcel.writeString(taskID)
        parcel.writeString(name)
        parcel.writeInt(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<JoinPerson> {
        override fun createFromParcel(parcel: Parcel): JoinPerson {
            return JoinPerson(parcel)
        }

        override fun newArray(size: Int): Array<JoinPerson?> {
            return arrayOfNulls(size)
        }
    }
}

