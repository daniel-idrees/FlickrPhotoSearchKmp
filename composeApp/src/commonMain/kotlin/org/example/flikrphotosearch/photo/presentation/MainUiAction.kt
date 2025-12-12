package org.example.flikrphotosearch.photo.presentation

import org.example.flikrphotosearch.app.config.BottomBarScreen

sealed interface MainUiAction {
    data class OnNavigateBackRequest(val fromScreen: BottomBarScreen) : MainUiAction
    data object ClearSearchHistory : MainUiAction
    data class RequestSearch(val searchQuery: String, val fromScreen: BottomBarScreen) : MainUiAction
    data class OnSearchQueryChange(val query: String) : MainUiAction
    data class RemoveSearchHistory(val index: Int) : MainUiAction
    data class OnSearchHistoryItemSelected(val searchQuery: String, val fromScreen: BottomBarScreen) : MainUiAction
}