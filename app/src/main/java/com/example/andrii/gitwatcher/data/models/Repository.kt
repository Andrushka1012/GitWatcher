package com.example.andrii.gitwatcher.data.models

import android.os.Parcel
import android.os.Parcelable
import com.example.andrii.gitwatcher.data.Database.RepositoryObjectDb
import io.realm.Realm

data class Repository constructor(val id:String?) : Parcelable {

    companion object CREATOR : Parcelable.Creator<Repository> {
        override fun createFromParcel(parcel: Parcel): Repository {
            return Repository(parcel)
        }

        override fun newArray(size: Int): Array<Repository?> {
            return arrayOfNulls(size)
        }

        fun getAllRepositoriesFromRealm(realm: Realm):ArrayList<Repository>{
            val realmResult = realm.where(RepositoryObjectDb::class.java).findAll()
            val list = ArrayList<Repository>()
            //realmResult.forEach{list+=Repository(it)}
            //val user = User("1","Andrii","https://avatars0.githubusercontent.com/u/33988987?v=4")
            //val repository = Repository("1","Repository","325654","Kotlin","15","32","12","15","10 Dec",user)
            //list.add(repository)
            return list
        }

    }

    var name:String? = null
    var size:String? = null
    var language:String? = null
    var watchers:String? = null
    var forks_count:String? = null
    var open_issues:String? = null
    var stargazers_count:String? = null
    var updated_at:String? = null
    var owner:User? = null
    var clone_url:String? = null

    constructor(parcel: Parcel) : this(parcel.readString()) {
        name = parcel.readString()
        size = parcel.readString()
        language = parcel.readString()
        watchers = parcel.readString()
        forks_count = parcel.readString()
        open_issues = parcel.readString()
        stargazers_count = parcel.readString()
        updated_at = parcel.readString()
        owner = parcel.readParcelable(User::class.java.classLoader)
        clone_url = parcel.readString()
    }


    constructor(repositoryObject: RepositoryObjectDb?) : this(repositoryObject?.repositoryId) {

    }

    constructor(repositoryId: String?, name: String?, size: String?, language: String?, watchers_count: String?, network_count: String?, open_issues: String?, stargazers_count: String?, updated_at: String?, owner: User?,clone_url:String?) : this(repositoryId) {
        this.name = name
        this.size = size
        this.language = language
        this.watchers = watchers_count
        this.forks_count = network_count
        this.open_issues = open_issues
        this.stargazers_count = stargazers_count
        this.updated_at = updated_at
        this.owner = owner
        this.clone_url = clone_url
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(size)
        parcel.writeString(language)
        parcel.writeString(watchers)
        parcel.writeString(forks_count)
        parcel.writeString(open_issues)
        parcel.writeString(stargazers_count)
        parcel.writeString(updated_at)
        parcel.writeParcelable(owner, flags)
        parcel.writeString(clone_url)
    }

    override fun describeContents(): Int {
        return 0
    }


}
