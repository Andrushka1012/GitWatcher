package com.example.andrii.gitwatcher.modules.profile.presenters

import android.util.Log
import android.widget.Toast
import com.example.andrii.gitwatcher.base.BasePresenter.BasePresenter
import com.example.andrii.gitwatcher.data.GItHubAPI.GitHubClient
import com.example.andrii.gitwatcher.data.models.User
import com.example.andrii.gitwatcher.modules.profile.view.FollowerFollowingFragment
import com.example.andrii.gitwatcher.modules.profile.view.ProfileActivity
import com.example.andrii.gitwatcher.modules.profile.view.ProfilePresenter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class FollowerFollowingPresenter:BasePresenter<FollowerFollowingFragment>(){

    var activityPresenter: ProfilePresenter? = null

    override fun onAttach(view: FollowerFollowingFragment?) {
        super.onAttach(view)
        activityPresenter = (view?.getParentActivity() as ProfileActivity).getPresenter()
    }

    override fun onDetach() {
        super.onDetach()
        activityPresenter = null
    }

    fun provideFollowers() = activityPresenter?.followers

    fun provideFollowing() = activityPresenter?.following


    fun setupData(retrofit: Retrofit,login:String) {
        if (getView()?.getType() == FollowerFollowingFragment.TYPE_FOLLOWING) setupFollowing(retrofit,login)
            else setupFollower(retrofit,login)
    }

    private fun setupFollowing(retrofit: Retrofit,login:String){
        val client = retrofit.create(GitHubClient::class.java)
        val call = client.getFollowingForUser(login)

        call.enqueue(object : Callback<ArrayList<User>> {
            override fun onFailure(call: Call<ArrayList<User>>?, t: Throwable?) {
                Log.e("",t.toString())
                Toast.makeText(getView()!!.context,"Connection Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ArrayList<User>>?, response: Response<ArrayList<User>>?) {
                val list = response?.body()
                activityPresenter?.saveFollowing(list)
                getView()!!.setUpRecyclerView(list)
            }
        })

    }

    private fun setupFollower(retrofit: Retrofit,login:String){
        val client = retrofit.create(GitHubClient::class.java)
        val call = client.getFollowersForUser(login)

        call.enqueue(object : Callback<ArrayList<User>> {
            override fun onFailure(call: Call<ArrayList<User>>?, t: Throwable?) {
                Log.e("",t.toString())
                Toast.makeText(getView()!!.context,"Connection Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ArrayList<User>>?, response: Response<ArrayList<User>>?) {
                val list = response?.body()
                activityPresenter?.saveFollowers(list)
                getView()!!.setUpRecyclerView(list)
            }
        })
    }
}
