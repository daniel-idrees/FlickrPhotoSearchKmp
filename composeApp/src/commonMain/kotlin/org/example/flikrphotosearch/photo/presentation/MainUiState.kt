package org.example.flikrphotosearch.photo.presentation

import org.example.flikrphotosearch.core.presentation.content.ContentErrorConfig
import org.example.flikrphotosearch.photo.domain.Photo

data class MainUiState(
    val isLoading: Boolean = false,
    val error: ContentErrorConfig? = null,
    val searchQuery: String = "",
    val lastSearch: String = "",
    val photoList: List<Photo> = emptyList(),
    val searchHistory: List<String> = emptyList(),
)