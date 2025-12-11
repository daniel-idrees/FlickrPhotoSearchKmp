package org.example.flikrphotosearch.photo.presentation

import org.example.flikrphotosearch.app.config.BottomBarScreen

sealed interface MainUiEvent {
    data class OnNavigateBackRequest(val fromScreen: BottomBarScreen) : MainUiEvent
    data object ClearSearchHistory : MainUiEvent
    data class RequestSearch(val searchQuery: String, val fromScreen: BottomBarScreen) : MainUiEvent
    data class OnSearchQueryChange(val query: String) : MainUiEvent
    data class RemoveSearchHistory(val index: Int) : MainUiEvent
    data class OnSearchHistoryItemSelected(val searchQuery: String, val fromScreen: BottomBarScreen) : MainUiEvent
}