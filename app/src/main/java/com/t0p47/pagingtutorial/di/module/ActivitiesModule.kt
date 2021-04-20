package com.t0p47.pagingtutorial.di.module

import com.t0p47.pagingtutorial.ui.SearchRepositoriesActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = [SearchRepositoriesActivityModule::class])
    abstract fun contributeSearchRepositoriesActivity(): SearchRepositoriesActivity

}