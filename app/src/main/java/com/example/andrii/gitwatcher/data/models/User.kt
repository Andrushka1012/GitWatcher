package com.example.andrii.gitwatcher.data.models

import android.os.Parcel
import android.os.Parcelable
import com.example.andrii.gitwatcher.data.Database.UserObjectDb
import io.realm.Realm

data class User constructor(var id:String?) : Parcelable {

    companion object CREATOR : Parcelable.Creator<User> {

        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }

        fun getAllUsersFromRealm(realm:Realm):ArrayList<User>{
            val realmResult = realm.where(UserObjectDb::class.java).findAll()
            val list = ArrayList<User>()
            realmResult.forEach{list += User(it)}

            return list
        }

    }

    var login:String? = null
    var avatar_url:String? = null
    var name:String? = null
    var company:String? = null
    var blog:String? = null
    var location:String? = null
    var bio:String? = null
    var followers:Int? = null
    var following:Int? = null
    var updated_at:String? = null

    constructor(parcel: Parcel) : this(parcel.readString()) {
        login = parcel.readString()
        avatar_url = parcel.readString()
        name = parcel.readString()
        company = parcel.readString()
        blog = parcel.readString()
        location = parcel.readString()
        bio = parcel.readString()
        followers = parcel.readValue(Int::class.java.classLoader) as? Int
        following = parcel.readValue(Int::class.java.classLoader) as? Int
        updated_at = parcel.readString()
    }

    constructor(userObject: UserObjectDb?) : this(userObject?.id) {
        login = userObject?.login
        avatar_url = userObject?.avatarUrl
    }

    constructor(id: String?, login: String?, avatar_url: String?, name: String?, company: String?, blog: String?, location: String?, bio: String?, followers: Int?, following: Int?, updated_at: String?) : this(id) {
        this.login = login
        this.avatar_url = avatar_url
        this.name = name
        this.company = company
        this.blog = blog
        this.location = location
        this.bio = bio
        this.followers = followers
        this.following = following
        this.updated_at = updated_at
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(login)
        parcel.writeString(avatar_url)
        parcel.writeString(name)
        parcel.writeString(company)
        parcel.writeString(blog)
        parcel.writeString(location)
        parcel.writeString(bio)
        parcel.writeValue(followers)
        parcel.writeValue(following)
        parcel.writeString(updated_at)
    }

    override fun describeContents(): Int {
        return 0
    }

}
