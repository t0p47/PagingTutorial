package com.t0p47.pagingtutorial.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.t0p47.pagingtutorial.di.ViewModelKey
import com.t0p47.pagingtutorial.factory.ViewModelFactory
import com.t0p47.pagingtutorial.ui.SearchRepositoriesViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchRepositoriesViewModel::class)
    abstract fun bindSearchRepositoriesViewModel(searchRepositoriesViewModel: SearchRepositoriesViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

}