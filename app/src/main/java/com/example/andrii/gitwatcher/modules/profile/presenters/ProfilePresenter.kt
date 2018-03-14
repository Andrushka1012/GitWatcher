package com.example.andrii.gitwatcher.modules.profile.view

import android.os.Bundle
import com.example.andrii.gitwatcher.base.BasePresenter.BasePresenter
import com.example.andrii.gitwatcher.data.models.Organization
import com.example.andrii.gitwatcher.data.models.Repository
import com.example.andrii.gitwatcher.data.models.User

class ProfilePresenter:BasePresenter<ProfileActivity>(){
    companion object {
        private val USER_SAVED_INSTANCE = "USER_SAVED_INSTANCE"
        private val ORGANIZATION_SAVED_INSTANCE = "ORGANIZATION_SAVED_INSTANCE"
        private val FOLLOWING_SAVED_INSTANCE = "FOLLOWING_SAVED_INSTANCE"
        private val FOLLOWERS_SAVED_INSTANCE = "FOLLOWERS_SAVED_INSTANCE"
        private val REPOSITORIES_SAVED_INSTANCE = "REPOSITORIES_SAVED_INSTANCE"
    }

    var user:User? = null
    var organizations:ArrayList<Organization>? = null
    var repositories:ArrayList<Repository>? = null
    var followers:ArrayList<User>? = null
    var following:ArrayList<User>? = null


    fun saveOverviewPersonData(user: User?){
        this.user = user
    }

    fun saveOverViewOrganizations(organizations:ArrayList<Organization>?){
        this.organizations = organizations
    }

    fun saveRepositories(repositories: ArrayList<Repository>?){
        this.repositories = repositories
    }

    fun saveFollowers(list:ArrayList<User>?){
        followers = list
    }

    fun saveFollowing(list:ArrayList<User>?){
        following = list
    }

    fun saveInstances(outState: Bundle?) {
        outState?.putParcelable(USER_SAVED_INSTANCE,user)
        outState?.putParcelableArrayList(ORGANIZATION_SAVED_INSTANCE,organizations)
        outState?.putParcelableArrayList(FOLLOWERS_SAVED_INSTANCE,followers)
        outState?.putParcelableArrayList(FOLLOWING_SAVED_INSTANCE,following)
        outState?.putParcelableArrayList(REPOSITORIES_SAVED_INSTANCE,repositories)
    }

    fun loadInstances(outState: Bundle?){
        user = outState?.getParcelable(USER_SAVED_INSTANCE)
        organizations = outState?.getParcelableArrayList(ORGANIZATION_SAVED_INSTANCE)
        following = outState?.getParcelableArrayList(FOLLOWING_SAVED_INSTANCE)
        followers = outState?.getParcelableArrayList(FOLLOWERS_SAVED_INSTANCE)
        repositories = outState?.getParcelableArrayList(REPOSITORIES_SAVED_INSTANCE)
    }

}
