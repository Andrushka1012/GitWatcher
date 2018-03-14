package com.example.andrii.gitwatcher.modules.profile.presenters

import android.util.Log
import android.widget.Toast
import com.example.andrii.gitwatcher.base.BasePresenter.BasePresenter
import com.example.andrii.gitwatcher.data.GItHubAPI.GitHubClient
import com.example.andrii.gitwatcher.data.models.Repository
import com.example.andrii.gitwatcher.modules.profile.view.ProfileActivity
import com.example.andrii.gitwatcher.modules.profile.view.ProfilePresenter
import com.example.andrii.gitwatcher.modules.profile.view.ProfileRepositoriesFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class ProfileRepositoriesPresenter:BasePresenter<ProfileRepositoriesFragment>(){
    var activityPresenter: ProfilePresenter? = null

    override fun onAttach(view: ProfileRepositoriesFragment?) {
        super.onAttach(view)
        activityPresenter = (view?.getParentActivity() as ProfileActivity).getPresenter()
    }

    override fun onDetach() {
        super.onDetach()
        activityPresenter = null
    }

    fun provideRepositories() = activityPresenter?.repositories

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
                activityPresenter?.saveRepositories(list)

                getView()!!.setUpRecyclerView(list)
            }
        })
    }

}