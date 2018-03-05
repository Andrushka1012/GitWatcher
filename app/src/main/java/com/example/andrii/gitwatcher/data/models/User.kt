package com.example.andrii.gitwatcher.data.models

import com.example.andrii.gitwatcher.data.Database.UserObjectDb
import io.realm.Realm

class User{

    companion object {
        fun getAllUsersFromRealm(realm:Realm):List<User>{
            val realmResult = realm.where(UserObjectDb::class.java).findAll()
            val list = ArrayList<User>()
            realmResult.forEach{list+=User(it)}

            return list
        }
    }

    var userId:String? = null

    constructor(userObject: UserObjectDb?){
        userId = userObject?.userId
    }

}
