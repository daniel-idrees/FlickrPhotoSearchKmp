package org.example.flikrphotosearch.di

import org.example.flikrphotosearch.core.data.HttpClientFactory
import org.example.flikrphotosearch.photo.data.network.KtorRemotePhotoDataSource
import org.example.flikrphotosearch.photo.data.network.RemotePhotoDataSource
import org.example.flikrphotosearch.photo.data.repository.PhotoNetworkRepository
import org.example.flikrphotosearch.photo.domain.PhotoRepository
import org.example.flikrphotosearch.photo.presentation.MainViewModel
import org.example.flikrphotosearch.photo.presentation.search.SearchViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val platformModule: Module

val sharedModule = module {
    single { HttpClientFactory.create(get()) }
    singleOf(::KtorRemotePhotoDataSource).bind<RemotePhotoDataSource>()
    singleOf(::PhotoNetworkRepository).bind<PhotoRepository>()

    viewModelOf(::MainViewModel)
    viewModelOf(::SearchViewModel)
}