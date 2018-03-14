package com.example.andrii.gitwatcher.di.module

import com.example.andrii.gitwatcher.MyApplication
import com.example.andrii.gitwatcher.data.GItHubAPI.GitHubClient
import dagger.Module
import dagger.Provides
import io.realm.Realm
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module class AppModule(val app:MyApplication){

    @Provides
    @Singleton
    fun provideApp() = app

    @Provides
    @Singleton fun provideRealmUsersObjectDb():Realm = Realm.getDefaultInstance()

    @Provides
    @Singleton fun provideRetrofitGithubAPI():Retrofit = Retrofit.Builder()
            .baseUrl(GitHubClient.base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}