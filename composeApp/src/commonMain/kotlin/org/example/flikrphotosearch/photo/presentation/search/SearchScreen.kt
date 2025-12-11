@file:OptIn(ExperimentalFoundationApi::class)

package org.example.flikrphotosearch.photo.presentation.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import flickrphotosearch.composeapp.generated.resources.Res
import flickrphotosearch.composeapp.generated.resources.main_view_model_success_result_title
import flickrphotosearch.composeapp.generated.resources.search_screen_search_button_text
import flickrphotosearch.composeapp.generated.resources.search_screen_search_field_label
import flickrphotosearch.composeapp.generated.resources.search_screen_title
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.example.flikrphotosearch.app.config.BottomBarScreen
import org.example.flikrphotosearch.app.theme.FlickrPhotoSearchTheme
import org.example.flikrphotosearch.core.presentation.SPACING_LARGE
import org.example.flikrphotosearch.core.presentation.SPACING_MEDIUM
import org.example.flikrphotosearch.core.presentation.UiText
import org.example.flikrphotosearch.core.presentation.content.ContentError
import org.example.flikrphotosearch.core.presentation.content.ContentErrorConfig
import org.example.flikrphotosearch.core.presentation.content.ContentScreen
import org.example.flikrphotosearch.core.presentation.content.ContentTitle
import org.example.flikrphotosearch.core.presentation.keyboardAsState
import org.example.flikrphotosearch.photo.domain.Photo
import org.example.flikrphotosearch.photo.presentation.MainUiEvent
import org.example.flikrphotosearch.photo.presentation.MainUiState
import org.example.flikrphotosearch.photo.presentation.MainViewModel
import org.example.flikrphotosearch.photo.presentation.components.ImageOverlayView
import org.example.flikrphotosearch.photo.presentation.components.PhotoListItemView
import org.example.flikrphotosearch.photo.presentation.components.SearchFieldView
import org.example.flikrphotosearch.photo.presentation.components.TextBodyMedium
import org.example.flikrphotosearch.photo.presentation.components.TopArrowIcon
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun SearchScreen(
    mainViewModel: MainViewModel,
) {
    val searchViewModel = koinViewModel<SearchViewModel>()

    val mainViewState by mainViewModel.viewState.collectAsStateWithLifecycle()
    val searchViewState by searchViewModel.viewState.collectAsStateWithLifecycle()

    ContentScreen(
        isLoading = mainViewState.isLoading,
    ) { paddingValues ->
        Content(
            photoList = mainViewState.photoList,
            lastSearch = mainViewState.lastSearch,
            searchQuery = mainViewState.searchQuery,
            errorConfig = mainViewState.error,
            searchHistory = mainViewState.searchHistory,
            searchUiState = searchViewState,
            onMainUiEventSend = { mainViewModel.setEvent(it) },
            onSearchUiEventSend = { searchViewModel.setEvent(it) },
            paddingValues = paddingValues
        )
    }
}

@Composable
private fun Content(
    searchUiState: SearchUiState,
    onMainUiEventSend: (MainUiEvent) -> Unit,
    onSearchUiEventSend: (SearchUiEvent) -> Unit,
    paddingValues: PaddingValues,
    photoList: List<Photo>,
    lastSearch: String,
    searchQuery: String,
    errorConfig: ContentErrorConfig?,
    searchHistory: List<String>,
) {
    val hasError = errorConfig != null
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val showFab by remember {
        derivedStateOf {
            lazyListState.firstVisibleItemIndex > 5 //show floating button after 5 items
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    paddingValues = PaddingValues(
                        start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                        end = paddingValues.calculateEndPadding(LayoutDirection.Ltr)
                    )
                )
        ) {
            val photosResultAvailable = photoList.isNotEmpty()
            if (!photosResultAvailable) {
                ContentTitle(
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(Res.string.search_screen_title),
                )
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f),
                state = lazyListState
            ) {
                stickyHeader {
                    val prefilledText =
                        if (photosResultAvailable) lastSearch else searchQuery

                    val shouldShowButton = !photosResultAvailable

                    SearchFieldView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .then(if (!photosResultAvailable) Modifier.padding(top = SPACING_LARGE.dp) else Modifier),
                        searchInputPrefilledText = prefilledText,
                        searchHistory = searchHistory,
                        label = stringResource(Res.string.search_screen_search_field_label),
                        shouldHaveFocus = !photosResultAvailable,
                        buttonText = if (shouldShowButton && !hasError) stringResource(
                            Res.string.search_screen_search_button_text
                        ) else null,
                        searchErrorReceived = hasError,
                        doOnSearchRequest = { text ->
                            onMainUiEventSend(
                                MainUiEvent.RequestSearch(
                                    searchQuery = text,
                                    BottomBarScreen.Search
                                )
                            )
                        },
                        doOnSearchHistoryDropDownItemClick = { text ->
                            onMainUiEventSend(
                                MainUiEvent.OnSearchHistoryItemSelected(
                                    searchQuery = text,
                                    BottomBarScreen.Home
                                )
                            )
                        },
                        doOnSearchTextChange = { text ->
                            onMainUiEventSend(
                                MainUiEvent.OnSearchQueryChange(
                                    text
                                )
                            )
                        },
                        doOnClearHistoryClick = { index ->
                            onMainUiEventSend(MainUiEvent.RemoveSearchHistory(index))
                        }
                    )
                }

                if (photosResultAvailable) {
                    item {
                        TextBodyMedium(
                            Modifier
                                .fillMaxWidth()
                                .padding(vertical = SPACING_MEDIUM.dp),
                            text = stringResource(
                                Res.string.main_view_model_success_result_title,
                                lastSearch
                            ),
                        )
                    }
                    items(photoList) { photo ->
                        PhotoListItemView(
                            modifier = Modifier
                                .fillMaxWidth(),
                            photo = photo,
                            onPhotoClick = {
                                    onSearchUiEventSend(SearchUiEvent.OnPhotoClick(photo))
                            }
                        )
                    }
                } else if (errorConfig != null) {
                    item {
                        ContentError(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(
                                    paddingValues = PaddingValues(
                                        top = SPACING_MEDIUM.dp,
                                        bottom = paddingValues.calculateBottomPadding()
                                    )
                                ),
                            contentErrorConfig = errorConfig
                        )
                    }
                } else {
                    item {
                        val isKeyboardOpen by keyboardAsState()
                        LaunchedEffect(isKeyboardOpen) {
                            delay(300)
                            if (!isKeyboardOpen) {
                                onMainUiEventSend(
                                    MainUiEvent.OnNavigateBackRequest(
                                        BottomBarScreen.Search
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
        AnimatedVisibility(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(SPACING_MEDIUM.dp), visible = showFab
        ) {
            FloatingActionButton(
                onClick = {
                    coroutineScope.launch {
                        lazyListState.scrollToItem(0)
                    }
                },
                modifier = Modifier

            ) {
                TopArrowIcon(modifier = Modifier.size(SPACING_LARGE.dp))
            }
        }
    }

    AnimatedVisibility(
        visible = searchUiState.overlayedPhoto != null,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        searchUiState.overlayedPhoto?.let { image ->
            ImageOverlayView(
                imageUrl = image.url,
                onClose = {
                    onSearchUiEventSend(SearchUiEvent.ClearPhotoOverlay)
                }
            )
        }
    }
}

@Preview
@Composable
private fun SearchPreview(
    @PreviewParameter(SearchPreviewParameterProvider::class) viewState: MainUiState,
) {
    FlickrPhotoSearchTheme {
        Content(
            photoList = viewState.photoList,
            lastSearch = viewState.lastSearch,
            searchQuery = viewState.searchQuery,
            errorConfig = viewState.error,
            searchHistory = viewState.searchHistory,
            searchUiState = SearchUiState(overlayedPhoto = null),
            onSearchUiEventSend = {},
            onMainUiEventSend = {},
            paddingValues = PaddingValues(1.dp)
        )
    }
}

private class SearchPreviewParameterProvider : PreviewParameterProvider<MainUiState> {
    val viewState = MainUiState()

    override val values = sequenceOf(
        viewState,
        viewState.copy(searchQuery = "query"),
        viewState.copy(isLoading = true),
        viewState.copy(
            searchQuery = "query",
            photoList = listOf(
                Photo(
                    title = "Photo One",
                    url = "url",
                    isPublic = true,
                    isFriend = true,
                    isFamily = true
                ),
                Photo(
                    title = "Photo One",
                    url = "url",
                    isPublic = true,
                    isFriend = true,
                    isFamily = true
                )
            ),
            searchHistory = listOf("query", "query2"),
            lastSearch = "query"
        ), viewState.copy(
            searchQuery = "query",
            photoList = listOf(
                Photo(
                    title = "Photo Two",
                    url = "url",
                    isPublic = false,
                    isFriend = false,
                    isFamily = false
                )
            ),
            searchHistory = listOf("query", "query2"),
            lastSearch = "query"
        ), viewState.copy(
            searchQuery = "query",
            photoList = listOf(
                Photo(
                    title = "Photo Three",
                    url = "url",
                    isPublic = false,
                    isFriend = true,
                    isFamily = false
                )
            ),
            searchHistory = listOf("query", "query2"),
            lastSearch = "query"
        ),
        viewState.copy(
            searchQuery = "query",
            photoList = emptyList(),
            searchHistory = listOf("query", "query2"),
            error = ContentErrorConfig(
                errorTitleRes = ContentErrorConfig.ErrorMessage.Text(UiText.DynamicString("Something went wrong")),
                errorSubTitleRes = ContentErrorConfig.ErrorMessage.Text(UiText.DynamicString("Please check something and try again.")),
            ) {}
        )
    )
}