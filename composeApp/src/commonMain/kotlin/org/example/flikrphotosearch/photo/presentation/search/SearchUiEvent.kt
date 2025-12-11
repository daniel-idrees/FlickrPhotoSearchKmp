package org.example.flikrphotosearch.photo.presentation.search

import org.example.flikrphotosearch.photo.domain.Photo

sealed interface SearchUiEvent {
    data class OnPhotoClick(val photo: Photo) : SearchUiEvent
    data object ClearPhotoOverlay : SearchUiEvent
}