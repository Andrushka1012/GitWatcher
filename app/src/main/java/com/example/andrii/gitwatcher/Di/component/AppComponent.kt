package com.example.andrii.gitwatcher.Di.component

import com.example.andrii.gitwatcher.Di.module.AppModule
import com.example.andrii.gitwatcher.Di.module.BaseModule
import com.example.andrii.gitwatcher.MyApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(app: MyApplication)

    fun plus(startModule: BaseModule): BaseComponent
}