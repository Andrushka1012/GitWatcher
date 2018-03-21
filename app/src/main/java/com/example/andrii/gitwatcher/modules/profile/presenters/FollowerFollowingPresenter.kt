package com.example.andrii.gitwatcher.modules.profile.presenters

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.andrii.gitwatcher.base.BasePresenter.BasePresenter
import com.example.andrii.gitwatcher.data.GItHubAPI.GitHubClient
import com.example.andrii.gitwatcher.data.models.User
import com.example.andrii.gitwatcher.modules.profile.view.FollowerFollowingFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class FollowerFollowingPresenter:BasePresenter<FollowerFollowingFragment>(){

    companion object {
        private const val FOLLOWING_SAVED_INSTANCE = "FOLLOWING_SAVED_INSTANCE"
        private const val FOLLOWERS_SAVED_INSTANCE = "FOLLOWERS_SAVED_INSTANCE"
    }

    var followers:ArrayList<User>? = null
    var following:ArrayList<User>? = null

    fun provideFollowers() = followers

    fun provideFollowing() = following


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
                following = list
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
                followers = list
                getView()!!.setUpRecyclerView(list)
            }
        })
    }

    fun saveInstances(outState: Bundle?) {
        outState?.putParcelableArrayList(FOLLOWERS_SAVED_INSTANCE,followers)
        outState?.putParcelableArrayList(FOLLOWING_SAVED_INSTANCE,following)
    }

    fun loadInstances(outState: Bundle?){
        following = outState?.getParcelableArrayList(FOLLOWING_SAVED_INSTANCE)
        followers = outState?.getParcelableArrayList(FOLLOWERS_SAVED_INSTANCE)

    }

}
