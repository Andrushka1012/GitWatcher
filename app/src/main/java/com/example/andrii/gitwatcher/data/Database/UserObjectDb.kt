package com.example.andrii.gitwatcher.data.Database

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class UserObjectDb:RealmObject(){

    @PrimaryKey
    @Required
    var userId:String? = null


}
