package com.example.andrii.gitwatcher.di.component

import com.example.andrii.gitwatcher.MyApplication
import com.example.andrii.gitwatcher.di.module.AppModule
import com.example.andrii.gitwatcher.di.module.BaseModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(app: MyApplication)

    fun plus(startModule: BaseModule): BaseComponent
}