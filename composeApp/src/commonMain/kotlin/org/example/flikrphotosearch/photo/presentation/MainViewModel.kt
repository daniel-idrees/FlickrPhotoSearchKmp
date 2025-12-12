package org.example.flikrphotosearch.photo.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import flickrphotosearch.composeapp.generated.resources.Res
import flickrphotosearch.composeapp.generated.resources.main_view_model_search_failed_error_title
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.flikrphotosearch.app.config.BottomBarScreen
import org.example.flikrphotosearch.core.domain.onError
import org.example.flikrphotosearch.core.domain.onSuccess
import org.example.flikrphotosearch.core.presentation.content.ContentErrorConfig
import org.example.flikrphotosearch.core.presentation.toUiText
import org.example.flikrphotosearch.photo.data.repository.PhotoNetworkRepository

class MainViewModel(
    private val photoRepository: PhotoNetworkRepository,
) : ViewModel() {

    private val _effect = Channel<MainUiEffect>(capacity = 32)
    val effect = _effect.receiveAsFlow()

    private val _action: MutableSharedFlow<MainUiAction> = MutableSharedFlow(extraBufferCapacity = 3)

    private val _homeUiState: MutableStateFlow<MainUiState> = MutableStateFlow(MainUiState())

    val viewState = _homeUiState.asStateFlow()

    fun setAction(event: MainUiAction) {
        viewModelScope.launch {
            _action.emit(event)
        }
    }

    init {
        viewModelScope.launch {
            _action.collect(::handleAction)
        }
    }

    private fun handleAction(event: MainUiAction) {
        when (event) {
            is MainUiAction.RequestSearch -> doOnSearchRequest(event)
            is MainUiAction.OnNavigateBackRequest -> setEffect { MainUiEffect.Navigation.Pop(event.fromScreen) }
            is MainUiAction.OnSearchQueryChange -> _homeUiState.update {
                it.copy(
                    error = null,
                    searchQuery = event.query
                )
            }

            MainUiAction.ClearSearchHistory -> _homeUiState.update {
                it.copy(
                    searchHistory = emptyList()
                )
            }

            is MainUiAction.RemoveSearchHistory -> {
                _homeUiState.update {
                    it.copy(
                        searchHistory = it.searchHistory.filterIndexed { index, _ -> index != event.index }
                    )
                }
            }

            is MainUiAction.OnSearchHistoryItemSelected -> {
                if (event.fromScreen != BottomBarScreen.Search) {
                    switchToSearchScreen()
                }

                _homeUiState.update {
                    it.copy(
                        searchQuery = event.searchQuery
                    )
                }

                search(searchQuery = event.searchQuery)
            }
        }
    }

    private fun doOnSearchRequest(event: MainUiAction.RequestSearch) {
        if (event.searchQuery.isEmpty() || event.searchQuery.isBlank()) {
            //setEffect {
                //MainUiEffect.ShowError(Res.string.main_view_model_empty_text_error_toast_text)
            //}
            //TODO
        } else {
            if (event.fromScreen != BottomBarScreen.Search) {
                switchToSearchScreen()
            }
            val searchQuery = event.searchQuery.trim() // remove whitespaces
            search(searchQuery = searchQuery)
        }
    }

    private fun switchToSearchScreen() {
        setEffect { MainUiEffect.Navigation.SwitchScreen(BottomBarScreen.Search) }
    }

    private fun search(searchQuery: String) {
        _homeUiState.update {
            it.copy(
                isLoading = true,
                error = null,
                lastSearch = searchQuery,
                searchHistory = getUpdatedSearchHistory(searchQuery),
            )
        }

        viewModelScope.launch {
            photoRepository
                .searchPhotos(searchQuery)
                .onSuccess { searchResults ->
                    _homeUiState.update {
                        it.copy(
                            isLoading = false,
                            photoList = searchResults
                        )
                    }
                }.onError { error ->
                    _homeUiState.update {
                        it.copy(
                            isLoading = false,
                            photoList = emptyList(),
                            error = ContentErrorConfig(
                                errorTitleRes = ContentErrorConfig.ErrorMessage.Resource(Res.string.main_view_model_search_failed_error_title),
                                errorSubTitleRes = ContentErrorConfig.ErrorMessage.Text(error.toUiText()),
                                onRetry = { search(searchQuery) },
                            )
                        )
                    }
                }
        }
    }

    private fun getUpdatedSearchHistory(searchQuery: String): List<String> {
        val searchHistory = ArrayDeque(viewState.value.searchHistory)
        searchHistory.addFirst(searchQuery)
        return searchHistory
    }

    private fun setEffect(builder: () -> MainUiEffect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }
}
