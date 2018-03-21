package com.example.andrii.gitwatcher.modules.file

import android.util.Log
import android.widget.Toast
import com.example.andrii.gitwatcher.base.BasePresenter.BasePresenter
import com.example.andrii.gitwatcher.data.GItHubAPI.GitHubClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class FileActivityPresenter:BasePresenter<FileActivity>(){

    fun downloadContent(url:String){
        val contentUrl = url.replace("https://raw.githubusercontent.com/","")
                .replace("%2F","/",true)

        Log.d("pizdec",contentUrl)

        val retrofit = Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val client = retrofit.create(GitHubClient::class.java)
        val call = client.getFile(contentUrl)
        call.enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>?, t: Throwable?) {
                Log.e("pizdec",call.toString()+"  " +t.toString())
                Toast.makeText(getView(),"Connection Error", Toast.LENGTH_LONG).show()
                getView()!!.showFile(null)
            }

            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                Log.e("pizdec",call.toString()+"  " +response.toString())
                val markdown = response?.body()
                getView()!!.showFile(markdown)
            }
        })

    }

}