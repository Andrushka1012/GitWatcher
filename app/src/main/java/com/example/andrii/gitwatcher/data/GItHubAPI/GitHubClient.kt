package com.example.andrii.gitwatcher.data.GItHubAPI

import com.example.andrii.gitwatcher.data.models.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubClient{

    companion object {
        val base_url = "https://api.github.com/"
    }

    @GET("search/repositories?client_id=5720488d90cd333cdb6c&client_secret=156d96058524a4b068eb76a8ec32f4de2f57eecb")
    fun searchRepositories(@Query(value = "q",encoded = true) query:String,@Query("page") page:Long):Call<SearchRepisotory>

    @GET("/search/users?client_id=5720488d90cd333cdb6c&client_secret=156d96058524a4b068eb76a8ec32f4de2f57eecb")
    fun searchUser(@Query(value = "q",encoded = true) query:String,@Query("page") page:Long):Call<SearchUsers>

    @GET("/users/{login}?client_id=5720488d90cd333cdb6c&client_secret=156d96058524a4b068eb76a8ec32f4de2f57eecb")
    fun getUser(@Path("login") login:String):Call<User>

    @GET("/users/{login}/orgs?client_id=5720488d90cd333cdb6c&client_secret=156d96058524a4b068eb76a8ec32f4de2f57eecb")
    fun getOrganizationsForUser(@Path("login") login:String):Call<ArrayList<Organization>>

    @GET("/users/{login}/followers?client_id=5720488d90cd333cdb6c&client_secret=156d96058524a4b068eb76a8ec32f4de2f57eecb")
    fun getFollowersForUser(@Path("login") login:String):Call<ArrayList<User>>

    @GET("/users/{login}/following?client_id=5720488d90cd333cdb6c&client_secret=156d96058524a4b068eb76a8ec32f4de2f57eecb")
    fun getFollowingForUser(@Path("login") login:String):Call<ArrayList<User>>

    @GET("/users/{login}/repos?client_id=5720488d90cd333cdb6c&client_secret=156d96058524a4b068eb76a8ec32f4de2f57eecb")
    fun getReposForUser(@Path("login") login:String):Call<ArrayList<Repository>>

    @GET("/{user}/{repo}/master/README.md")
    fun getReadMe(@Path("user") user:String,@Path("repo") repo:String):Call<String>

    @GET("/repos/{user}/{repo}/contents/{path}?client_id=5720488d90cd333cdb6c&client_secret=156d96058524a4b068eb76a8ec32f4de2f57eecb")
    fun getDirFiles(@Path("user") user:String,
                    @Path("repo") repo:String,
                    @Path("path") path:String)
            :Call<ArrayList<FileModel>>

    @GET("/{url}")
    fun getFile(@Path("url") url:String):Call<String>

}