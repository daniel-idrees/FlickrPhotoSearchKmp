package org.example.flikrphotosearch.di

import io.ktor.client.engine.HttpClientEngine
import org.koin.core.module.Module

actual val platformModule: Module
    get() = module {
        single<HttpClientEngine> { OkHttp.create() }
    }