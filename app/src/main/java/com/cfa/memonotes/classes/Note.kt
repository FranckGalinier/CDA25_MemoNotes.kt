package com.cfa.memonotes.classes

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Note(
    val titre: String? = "", val text: String? = "",
    var fileName: String? = "" ): Parcelable,Serializable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, p1: Int) {
        dest.writeString(titre)
        dest.writeString(text)
        dest.writeString(fileName)
    }

    companion object CREATOR : Parcelable.Creator<Note> {
        private val serialVersionUID: Long = 165464133

        override fun createFromParcel(parcel: Parcel): Note {
            return Note(parcel)
        }

        override fun newArray(size: Int): Array<Note?> {
            return arrayOfNulls(size)
        }
    }


}
