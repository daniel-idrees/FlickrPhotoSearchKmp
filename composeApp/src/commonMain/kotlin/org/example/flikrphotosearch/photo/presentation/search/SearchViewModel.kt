package org.example.flikrphotosearch.photo.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SearchViewModel() : ViewModel() {
    private val _event: MutableSharedFlow<SearchUiEvent> = MutableSharedFlow()

    private val _searchUiState: MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState())

    val viewState = _searchUiState.asStateFlow()

    fun setEvent(event: SearchUiEvent) {
        viewModelScope.launch {
            _event.emit(event)
        }
    }

    init {
        viewModelScope.launch {
            _event.collect(::handleEvent)
        }
    }

    private fun handleEvent(event: SearchUiEvent) {
        when (event) {
            SearchUiEvent.ClearPhotoOverlay -> {
                _searchUiState.update {
                    it.copy(
                        overlayedPhoto = null
                    )
                }
            }

            is SearchUiEvent.OnPhotoClick -> {
                _searchUiState.update {
                    it.copy(
                        overlayedPhoto = event.photo
                    )
                }
            }
        }
    }
}