package com.example.andrii.gitwatcher.Di.module

import com.example.andrii.gitwatcher.MyApplication
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module class AppModule(val app:MyApplication){
    @Provides
    @Singleton fun provideApp() = app

    @Provides
    @Singleton fun prowideRealmUsersObjcetDb():Realm = Realm.getDefaultInstance()

}