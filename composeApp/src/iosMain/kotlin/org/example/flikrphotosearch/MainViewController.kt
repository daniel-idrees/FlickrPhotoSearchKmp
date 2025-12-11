package org.example.flikrphotosearch

import androidx.compose.ui.window.ComposeUIViewController
import org.example.flikrphotosearch.app.App
import org.example.flikrphotosearch.di.initKoin

fun MainViewController() = ComposeUIViewController(
    configure = {
        initKoin()
    }
) { App() }