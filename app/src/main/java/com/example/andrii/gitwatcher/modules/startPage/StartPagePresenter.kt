package com.example.andrii.gitwatcher.modules.startPage


import android.content.Intent
import com.example.andrii.gitwatcher.base.BasePresenter.BasePresenter
import com.example.andrii.gitwatcher.data.models.Repository
import com.example.andrii.gitwatcher.data.models.User
import com.example.andrii.gitwatcher.modules.search.view.SearchActivity
import com.example.andrii.gitwatcher.modules.startPage.view.StartPageActivity
import com.example.andrii.gitwatcher.modules.startPage.view.StartView
import io.realm.Realm
import java.util.*

class StartPagePresenter : BasePresenter<StartView>(){

    fun provideUsersData(realm:Realm):ArrayList<User> = User.getAllUsersFromRealm(realm)

    fun provideRepositoriesData(realm:Realm):ArrayList<Repository> = Repository.getAllRepositoriesFromRealm(realm)

    fun onSearchItemSelected():Boolean {
        val context = getView() as StartPageActivity
        val intent = Intent(context,SearchActivity::class.java)
        context.startActivity(intent)

        return true
    }

}

