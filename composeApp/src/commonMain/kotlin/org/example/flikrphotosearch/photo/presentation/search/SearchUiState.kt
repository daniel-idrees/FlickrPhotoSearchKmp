package org.example.flikrphotosearch.photo.presentation.search

import org.example.flikrphotosearch.photo.domain.Photo

data class SearchUiState(
    val overlayedPhoto: Photo? = null
)