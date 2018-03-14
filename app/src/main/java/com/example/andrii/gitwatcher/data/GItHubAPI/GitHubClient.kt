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

    @GET("search/repositories")
    fun searchRepositories(@Query(value = "q",encoded = true) query:String,@Query("page") page:Long):Call<SearchRepisotory>

    @GET("/search/users")
    fun searchUser(@Query(value = "q",encoded = true) query:String,@Query("page") page:Long):Call<SearchUsers>

    @GET("/users/{login}")
    fun getUser(@Path("login") login:String):Call<User>

    @GET("/users/{login}/orgs")
    fun getOrganizationsForUser(@Path("login") login:String):Call<ArrayList<Organization>>

    @GET("/users/{login}/followers")
    fun getFollowersForUser(@Path("login") login:String):Call<ArrayList<User>>

    @GET("/users/{login}/following")
    fun getFollowingForUser(@Path("login") login:String):Call<ArrayList<User>>

    @GET("/users/{login}/repos")
    fun getReposForUser(@Path("login") login:String):Call<ArrayList<Repository>>

    @GET("/{user}/{repo}/master/README.md")
    fun getReadMe(@Path("user") user:String,@Path("repo") repo:String):Call<String>

}