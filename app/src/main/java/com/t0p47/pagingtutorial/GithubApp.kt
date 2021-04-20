package com.t0p47.pagingtutorial

import android.app.Application
import com.t0p47.pagingtutorial.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class GithubApp: Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate(){
        super.onCreate()

        AppInjector.init(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

}