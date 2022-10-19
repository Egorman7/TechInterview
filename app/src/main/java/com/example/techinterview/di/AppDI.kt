package com.example.techinterview.di

import com.example.techinterview.domain.repository.TVShowRepository
import com.example.techinterview.domain.repository.TVShowRepositoryImpl
import com.example.techinterview.ui.MainViewModel
import com.example.techinterview.usecase.LoadTvShowsUseCase
import com.example.techinterview.usecase.LoadTvShowsUseCaseImpl
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AppDI {
    private val viewModelModule = module {
        viewModel { MainViewModel(get()) }
    }

    private val repositoryModule = module {
        single<TVShowRepository> { TVShowRepositoryImpl(androidContext()) }
    }

    private val useCaseModule = module {
        factory<LoadTvShowsUseCase> { LoadTvShowsUseCaseImpl(get()) }
    }

    fun getModules(): List<Module> = listOf(viewModelModule, repositoryModule, useCaseModule)
}