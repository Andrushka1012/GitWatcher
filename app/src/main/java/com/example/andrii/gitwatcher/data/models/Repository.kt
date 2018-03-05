package com.example.andrii.gitwatcher.data.models

import com.example.andrii.gitwatcher.data.Database.RepositoryObjectDb
import io.realm.Realm

class Repository{

    companion object {
        fun getAllRepositoriesFromRealm(realm: Realm):List<Repository>{
            val realmResult = realm.where(RepositoryObjectDb::class.java).findAll()
            val list = ArrayList<Repository>()
            realmResult.forEach{list+=Repository(it)}

            return list
        }
    }

    constructor(repositoryObject: RepositoryObjectDb?){
        repositoryId = repositoryObject?.repositoryId
    }

    var repositoryId:String? = null

}
