package com.t0p47.pagingtutorial.di

import android.app.Application
import com.t0p47.pagingtutorial.GithubApp
import com.t0p47.pagingtutorial.di.module.ActivitiesModule
import com.t0p47.pagingtutorial.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        ActivitiesModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(githubApp: GithubApp)

}