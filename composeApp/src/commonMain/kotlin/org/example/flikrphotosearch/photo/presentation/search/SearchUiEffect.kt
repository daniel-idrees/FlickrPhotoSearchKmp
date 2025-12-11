package org.example.flikrphotosearch.photo.presentation.search

sealed interface SearchUiEffect {
    data object ShowPhotoOverlay : SearchUiEffect
    data object HidePhotoOverlay : SearchUiEffect
}