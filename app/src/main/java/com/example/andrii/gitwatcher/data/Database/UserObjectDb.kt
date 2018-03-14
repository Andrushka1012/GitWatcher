package com.example.andrii.gitwatcher.data.Database

import com.example.andrii.gitwatcher.data.models.User
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class UserObjectDb:RealmObject(){

    companion object {
        fun userToRealmObject(user:User):UserObjectDb{
            val objectDB = UserObjectDb()
            objectDB.id = user.id
            objectDB.login = user.login
            objectDB.avatarUrl = user.avatar_url

            return objectDB
        }
    }

    @PrimaryKey
    @Required
    var id:String? = null
    var login:String? = null
    var avatarUrl:String? = null


}
