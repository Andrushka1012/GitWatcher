package com.example.andrii.gitwatcher.data.models

import android.os.Parcel
import android.os.Parcelable

class Organization() : Parcelable {
    var id:String? = null
    var login:String? = null
    var url:String? = null
    var avatar_url:String? = null
    var description:String? = null

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        login = parcel.readString()
        url = parcel.readString()
        avatar_url = parcel.readString()
        description = parcel.readString()
    }

    constructor(id: String?, login: String?, url: String?, avatar_url: String?, description: String?) : this() {
         this.id = id
         this.login = login
         this.url = url
         this.avatar_url = avatar_url
         this.description = description
     }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(login)
        parcel.writeString(url)
        parcel.writeString(avatar_url)
        parcel.writeString(description)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Organization> {
        override fun createFromParcel(parcel: Parcel): Organization {
            return Organization(parcel)
        }

        override fun newArray(size: Int): Array<Organization?> {
            return arrayOfNulls(size)
        }
    }
}