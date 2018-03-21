package com.example.andrii.gitwatcher.data.models

import android.os.Parcel
import android.os.Parcelable

data class FileModel constructor(val name:String?,val type:String?,val download_url:String?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(type)
        parcel.writeString(download_url)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FileModel> {
        override fun createFromParcel(parcel: Parcel): FileModel {
            return FileModel(parcel)
        }

        override fun newArray(size: Int): Array<FileModel?> {
            return arrayOfNulls(size)
        }
    }

}
