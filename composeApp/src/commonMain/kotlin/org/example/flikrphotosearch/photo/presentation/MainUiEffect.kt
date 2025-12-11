package org.example.flikrphotosearch.photo.presentation

import org.example.flikrphotosearch.app.config.BottomBarScreen
import org.jetbrains.compose.resources.StringResource

sealed interface MainUiEffect {
    sealed interface Navigation : MainUiEffect {
        data class SwitchScreen(val toScreen: BottomBarScreen) : Navigation
        data class Pop(val fromScreen: BottomBarScreen) : Navigation
    }
}