package com.t0p47.pagingtutorial.di.module

import dagger.Module

@Module(includes = [ViewModelModule::class, DatabaseModule::class, NetworkModule::class])
class AppModule {
}