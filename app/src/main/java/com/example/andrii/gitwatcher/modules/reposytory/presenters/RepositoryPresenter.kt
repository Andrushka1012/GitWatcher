package com.example.andrii.gitwatcher.modules.reposytory.presenters

import android.graphics.drawable.Animatable
import android.widget.ImageButton
import com.example.andrii.gitwatcher.R
import com.example.andrii.gitwatcher.base.BasePresenter.BasePresenter
import com.example.andrii.gitwatcher.data.Database.RepositoryObjectDb
import com.example.andrii.gitwatcher.data.models.Repository
import com.example.andrii.gitwatcher.modules.reposytory.views.RepositoryActivity
import io.realm.Realm


class RepositoryPresenter: BasePresenter<RepositoryActivity>(){

    fun setupFollowOnClick(btn: ImageButton?,repo:Repository,realm:Realm){
        btn?.setOnClickListener{
                setupAnimation(btn)
                getView()?.followingStateChanged()
                doDbOperations(repo,realm)
            }
        }

    private fun doDbOperations(repo:Repository, realm:Realm){
        val realmObject = realm.where(RepositoryObjectDb::class.java)
                .equalTo("repositoryId", repo.id)
                .findFirst()

        if (realmObject != null) {
            realm.executeTransaction { realmObject.deleteFromRealm() }
        }
        else {
            val realmObjectRepo = RepositoryObjectDb.repositoryToRealmObject(repo)
            realm.executeTransaction { it.insertOrUpdate(realmObjectRepo) }
        }
    }

    fun setupAnimation(btn: ImageButton){
        btn.isSelected = !btn.isSelected
        btn.setImageResource(if (btn.isSelected) R.drawable.animated_plus else R.drawable.animated_minus)
        val drawable = btn.drawable
        if (drawable is Animatable) {
            (drawable as Animatable).start()
        }
    }
}

