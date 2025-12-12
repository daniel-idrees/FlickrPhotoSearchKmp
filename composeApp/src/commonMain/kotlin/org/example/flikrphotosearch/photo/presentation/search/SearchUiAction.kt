package org.example.flikrphotosearch.photo.presentation.search

import org.example.flikrphotosearch.photo.domain.Photo

sealed interface SearchUiAction {
    data class OnPhotoClick(val photo: Photo) : SearchUiAction
    data object ClearPhotoOverlay : SearchUiAction
}