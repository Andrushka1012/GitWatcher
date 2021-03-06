package com.example.andrii.gitwatcher

import android.app.Application

import com.example.andrii.gitwatcher.di.component.AppComponent
import com.example.andrii.gitwatcher.di.component.DaggerAppComponent
import com.example.andrii.gitwatcher.di.module.AppModule
import io.github.kbiakov.codeview.classifier.CodeProcessor
import io.realm.Realm

class MyApplication:Application(){

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        CodeProcessor.init(this);
        Realm.init(this)
        component.inject(this)
    }
}
