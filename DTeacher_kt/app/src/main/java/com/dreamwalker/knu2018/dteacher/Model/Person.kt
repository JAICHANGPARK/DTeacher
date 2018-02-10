package com.dreamwalker.knu2018.dteacher.Model

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by dpizarro
 */
class Person : Parcelable {

    var name: String
    var age: String
    var photoId: Int = 0
    var isSelected: Boolean = false

    constructor(name: String, age: String, photoId: Int, selected: Boolean) {
        this.name = name
        this.age = age
        this.photoId = photoId
        this.isSelected = selected
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeString(this.name)
        dest.writeString(this.age)
        dest.writeInt(this.photoId)
        dest.writeByte(if (isSelected) 1.toByte() else 0.toByte())
    }

    constructor(`in`: Parcel) {
        this.name = `in`.readString()
        this.age = `in`.readString()
        this.photoId = `in`.readInt()
        this.isSelected = `in`.readByte().toInt() != 0
    }

    companion object {

        val CREATOR: Parcelable.Creator<Person> = object : Parcelable.Creator<Person> {
            override fun createFromParcel(source: Parcel): Person {
                return Person(source)
            }

            override fun newArray(size: Int): Array<Person> {
                return arrayOfNulls(size)
            }
        }
    }
}
