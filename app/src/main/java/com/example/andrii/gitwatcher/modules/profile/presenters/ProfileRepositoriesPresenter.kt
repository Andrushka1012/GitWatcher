package com.example.andrii.gitwatcher.modules.profile.presenters

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.andrii.gitwatcher.base.BasePresenter.BasePresenter
import com.example.andrii.gitwatcher.data.GItHubAPI.GitHubClient
import com.example.andrii.gitwatcher.data.models.Repository
import com.example.andrii.gitwatcher.modules.profile.view.ProfileRepositoriesFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ProfileRepositoriesPresenter:BasePresenter<ProfileRepositoriesFragment>(){

    companion object {
        private val REPOSITORIES_SAVED_INSTANCE = "REPOSITORIES_SAVED_INSTANCE"
    }

    var repositories:ArrayList<Repository>? = null

    fun provideRepositories() = repositories

    fun setupData(login: String, retrofit: Retrofit) {
        val client = retrofit.create(GitHubClient::class.java)
        val call = client.getReposForUser(login)

        call.enqueue(object : Callback<ArrayList<Repository>> {
            override fun onFailure(call: Call<ArrayList<Repository>>?, t: Throwable?) {
                Log.e("",t.toString())
                Toast.makeText(getView()!!.context,"Connection Error", Toast.LENGTH_LONG).show()
            }

            override fun onResponse(call: Call<ArrayList<Repository>>?, response: Response<ArrayList<Repository>>?) {
                val list = response?.body()
                repositories = list

                getView()!!.setUpRecyclerView(list)
            }
        })
    }


    fun saveInstances(outState: Bundle?) {
        outState?.putParcelableArrayList(REPOSITORIES_SAVED_INSTANCE,repositories)
    }

    fun loadInstances(outState: Bundle?){
        repositories = outState?.getParcelableArrayList(REPOSITORIES_SAVED_INSTANCE)

    }

}