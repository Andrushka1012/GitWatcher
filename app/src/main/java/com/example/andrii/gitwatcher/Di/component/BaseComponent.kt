package com.example.andrii.gitwatcher.Di.component

import com.example.andrii.gitwatcher.Di.module.BaseModule
import com.example.andrii.gitwatcher.base.BaseView.BaseActivity
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = arrayOf(BaseModule::class))
interface BaseComponent {
    fun inject(activity:BaseActivity)
}