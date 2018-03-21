package com.example.andrii.gitwatcher.modules.reposytory.presenters

import android.util.Log
import android.widget.Toast
import com.example.andrii.gitwatcher.base.BasePresenter.BasePresenter
import com.example.andrii.gitwatcher.data.GItHubAPI.GitHubClient
import com.example.andrii.gitwatcher.modules.reposytory.views.ReadmeFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class ReadmePresenter: BasePresenter<ReadmeFragment>(){

    fun setUpData(userLogin: String, repoLogin: String) {
        val retrofit = Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val client = retrofit.create(GitHubClient::class.java)
        val call = client.getReadMe(userLogin,repoLogin)

        call.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>?, t: Throwable?) {
                Log.e("pizdec",call.toString()+"  " +t.toString())
                Toast.makeText(getView()!!.context,"Connection Error", Toast.LENGTH_LONG).show()
                getView()!!.showReadme(null)
            }

            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                val markdown = response?.body()
                getView()!!.showReadme(markdown)
            }
        })
    }

}