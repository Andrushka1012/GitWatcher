package com.example.andrii.gitwatcher.base.BaseView

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.example.andrii.gitwatcher.MyApplication
import com.example.andrii.gitwatcher.di.module.BaseModule
import io.realm.Realm
import retrofit2.Retrofit
import javax.inject.Inject

abstract class BaseActivity: AppCompatActivity(), MVPView{
    val Activity.app: MyApplication
        get() = application as MyApplication

    private val component by lazy { app.component.plus(BaseModule(this)) }

    @Inject
    lateinit var realm : Realm

    @Inject
    lateinit var retrofit: Retrofit


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        component.inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        //realm.close()
    }

    fun getStringResource(id:Int):String {
        return resources.getString(id)
    }


}