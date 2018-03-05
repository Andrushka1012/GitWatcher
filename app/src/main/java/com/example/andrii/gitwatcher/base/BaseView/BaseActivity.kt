package com.example.andrii.gitwatcher.base.BaseView

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.andrii.gitwatcher.Di.module.BaseModule
import com.example.andrii.gitwatcher.MyApplication
import io.realm.Realm
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity(), MVPView,BaseFragment.CallBack{
    val Activity.app: MyApplication
        get() = application as MyApplication

    val component by lazy { app.component.plus(BaseModule(this)) }

    @Inject lateinit var realm : Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component.inject(this)
    }



}