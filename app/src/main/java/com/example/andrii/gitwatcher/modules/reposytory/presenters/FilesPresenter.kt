package com.example.andrii.gitwatcher.modules.reposytory.presenters

import android.util.Log
import android.widget.Toast
import com.example.andrii.gitwatcher.base.BasePresenter.BasePresenter
import com.example.andrii.gitwatcher.data.GItHubAPI.GitHubClient
import com.example.andrii.gitwatcher.data.models.FileModel
import com.example.andrii.gitwatcher.modules.file.FileActivity
import com.example.andrii.gitwatcher.modules.reposytory.views.FilesFragment
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilesPresenter:BasePresenter<FilesFragment>(){

    fun fileClicked(fileModel: FileModel) {
        if (fileModel.type == "dir") getView()?.goToDir(fileModel)
        else {
            val context = getView()!!.activity
            val intent = FileActivity.newIntent(context,fileModel)
            context.startActivity(intent)
        }
    }

    fun goToDir(path: String) {
        val owner = getView()?.ownerName ?: ""
        val repo = getView()?.repoName ?: ""

        val retrofit = getView()!!.getParentActivity()!!.retrofit
        val client = retrofit.create(GitHubClient::class.java)
        val call = client.getDirFiles(owner,repo,path)
        Log.d("pizdec","path $path")
        getView()?.showProgress()

        call.enqueue(object :Callback<ArrayList<FileModel>>{
            override fun onFailure(call: Call<ArrayList<FileModel>>?, t: Throwable?) {
                Log.d("FilesPresenter",t.toString())
                Toast.makeText(getView()?.context,"Connection error",Toast.LENGTH_LONG).show()
                getView()?.hideProgress()
            }

            override fun onResponse(call: Call<ArrayList<FileModel>>?, response: Response<ArrayList<FileModel>>?) {

                val files = response?.body()
                getView()?.hideProgress()
                getView()?.setFiles(files)
            }
        })


    }

}