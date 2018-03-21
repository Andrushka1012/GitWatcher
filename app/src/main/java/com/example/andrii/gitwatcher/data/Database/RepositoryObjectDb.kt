package com.example.andrii.gitwatcher.data.Database

import com.example.andrii.gitwatcher.data.models.Repository
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required

open class RepositoryObjectDb: RealmObject(){

    companion object {
        fun repositoryToRealmObject(repo:Repository): RepositoryObjectDb {
            val objectDb = RepositoryObjectDb()
            objectDb.repositoryId = repo.id
            objectDb.name = repo.name
            objectDb.size = repo.size
            objectDb.language = repo.language
            objectDb.watchers = repo.watchers
            objectDb.forks_count = repo.forks_count
            objectDb.open_issues = repo.open_issues
            objectDb.stargazers_count = repo.stargazers_count
            objectDb.updated_at = repo.updated_at
            objectDb.ownerUrl = repo.owner?.avatar_url
            objectDb.ownerLogin = repo.owner?.login
            objectDb.ownerId = repo.owner?.id
            objectDb.clone_url = repo.clone_url

            return objectDb
        }
    }

    @PrimaryKey
    @Required
    var repositoryId:String? = null
    var name:String? = null
    var size:String? = null
    var language:String? = null
    var watchers:String? = null
    var forks_count:String? = null
    var open_issues:String? = null
    var stargazers_count:String? = null
    var updated_at:String? = null
    var ownerUrl: String? = null
    var ownerLogin: String? = null
    var ownerId: String? = null
    var clone_url:String? = null

}