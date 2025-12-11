package org.example.flikrphotosearch

import android.app.Application
import org.example.flikrphotosearch.di.initKoin
import org.koin.android.ext.koin.androidContext

class FlickrPhotoApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@FlickrPhotoApplication)
        }
    }
}