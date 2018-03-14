package com.example.andrii.gitwatcher.modules.profile.presenters

import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.andrii.gitwatcher.base.BasePresenter.BasePresenter
import com.example.andrii.gitwatcher.data.Database.UserObjectDb
import com.example.andrii.gitwatcher.data.GItHubAPI.GitHubClient
import com.example.andrii.gitwatcher.data.models.Organization
import com.example.andrii.gitwatcher.data.models.User
import com.example.andrii.gitwatcher.modules.profile.view.OverviewFragment
import com.example.andrii.gitwatcher.modules.profile.view.ProfileActivity
import com.example.andrii.gitwatcher.modules.profile.view.ProfilePresenter
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class OverviewFragmentPresenter:BasePresenter<OverviewFragment>(){
    var activityPresenter:ProfilePresenter? = null

    override fun onAttach(view: OverviewFragment?) {
        super.onAttach(view)
        activityPresenter = (view?.getParentActivity() as ProfileActivity).getPresenter()
    }

    override fun onDetach() {
        super.onDetach()
        activityPresenter = null
    }

    fun provideUser(): User? {
        return activityPresenter?.user
    }

    fun provideOrganisations(): ArrayList<Organization>? {
        return activityPresenter?.organizations
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
                Log.d("pizdec",response!!.message())
                val user = response.body()
                activityPresenter?.saveOverviewPersonData(user)
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
                Log.d("pizdec",response!!.message())
                val list = response?.body()
                activityPresenter?.saveOverViewOrganizations(list)
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

}