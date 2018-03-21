package com.example.andrii.gitwatcher.modules.profile.presenters

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.andrii.gitwatcher.base.BasePresenter.BasePresenter
import com.example.andrii.gitwatcher.data.Database.UserObjectDb
import com.example.andrii.gitwatcher.data.GItHubAPI.GitHubClient
import com.example.andrii.gitwatcher.data.models.Organization
import com.example.andrii.gitwatcher.data.models.User
import com.example.andrii.gitwatcher.modules.profile.view.OverviewFragment
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class OverviewFragmentPresenter:BasePresenter<OverviewFragment>(){

    companion object {
        private val USER_SAVED_INSTANCE = "USER_SAVED_INSTANCE"
        private val ORGANIZATION_SAVED_INSTANCE = "ORGANIZATION_SAVED_INSTANCE"
    }

    var user:User? = null
    var organizations:ArrayList<Organization>? = null

    fun provideUser(): User? {
        return user
    }

    fun provideOrganisations(): ArrayList<Organization>? {
        return organizations
    }

    fun loadData(login: String, retrofit: Retrofit) {
        val client = retrofit.create(GitHubClient::class.java)
        val callUserInfo = client.getUser(login)

        callUserInfo.enqueue(object : Callback<User>{
            override fun onFailure(call: Call<User>?, t: Throwable?) {
                Log.e("",t.toString())
                Toast.makeText(getView()!!.context,"Connection Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                val u = response?.body()
                user = u
                getView()!!.setUpUserData(user)
            }
        })

        val callOrganizations = client.getOrganizationsForUser(login)
        callOrganizations.enqueue(object :Callback<ArrayList<Organization>>{
            override fun onFailure(call: Call<ArrayList<Organization>>?, t: Throwable?) {
                Log.e("",t.toString())
                Toast.makeText(getView()!!.context,"Connection Error",Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ArrayList<Organization>>?, response: Response<ArrayList<Organization>>?) {
                val list = response?.body()
                organizations = list
                getView()!!.setOrganizations(list)
            }
        })
    }

    fun followUnfollowOnClick(realm: Realm, profileFollowUnfollowBtn: Button?, user: User) {
        profileFollowUnfollowBtn!!.setOnClickListener {
            getView()?.followingStateChanged()
            if (realm.where(UserObjectDb::class.java).equalTo("id", user.id).findFirst() != null) {
                realm.executeTransaction {
                    it.where(UserObjectDb::class.java)
                            .equalTo("id", user.id)
                            .findFirst()?.deleteFromRealm()
                }
            } else {
                val realmObject = UserObjectDb.userToRealmObject(user)
                realm.executeTransaction { it.insertOrUpdate(realmObject) }
            }

            getView()!!.chaneButtonText()
        }
    }

    fun saveInstances(outState: Bundle?) {
        outState?.putParcelable(USER_SAVED_INSTANCE,user)
        outState?.putParcelableArrayList(ORGANIZATION_SAVED_INSTANCE,organizations)

    }
    fun loadInstances(outState: Bundle?){
        user = outState?.getParcelable(USER_SAVED_INSTANCE)
        organizations = outState?.getParcelableArrayList(ORGANIZATION_SAVED_INSTANCE)
    }


}