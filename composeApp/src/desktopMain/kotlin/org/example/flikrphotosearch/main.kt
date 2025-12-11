package org.example.flikrphotosearch

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.flikrphotosearch.app.App
import org.example.flikrphotosearch.di.initKoin

fun main() = application {
    initKoin()
    Window(
        onCloseRequest = ::exitApplication,
        title = "FlickrPhotoSearch",
    ) {
        App()
    }
}