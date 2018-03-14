package com.example.andrii.gitwatcher.di.component

import com.example.andrii.gitwatcher.base.BaseView.BaseActivity
import com.example.andrii.gitwatcher.di.module.BaseModule
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = arrayOf(BaseModule::class))
interface BaseComponent {
    fun inject(activity:BaseActivity)
}