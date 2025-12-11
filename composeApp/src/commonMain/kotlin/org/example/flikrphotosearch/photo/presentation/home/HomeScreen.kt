package org.example.flikrphotosearch.photo.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import flickrphotosearch.composeapp.generated.resources.Res
import flickrphotosearch.composeapp.generated.resources.home_screen_search_button_text
import flickrphotosearch.composeapp.generated.resources.home_screen_search_field_label
import flickrphotosearch.composeapp.generated.resources.home_screen_title
import org.example.flikrphotosearch.app.config.BottomBarScreen
import org.example.flikrphotosearch.app.theme.FlickrPhotoSearchTheme
import org.example.flikrphotosearch.core.presentation.SPACING_LARGE
import org.example.flikrphotosearch.core.presentation.content.ContentScreen
import org.example.flikrphotosearch.core.presentation.content.ContentTitle
import org.example.flikrphotosearch.photo.domain.Photo
import org.example.flikrphotosearch.photo.presentation.MainUiEvent
import org.example.flikrphotosearch.photo.presentation.MainUiState
import org.example.flikrphotosearch.photo.presentation.MainViewModel
import org.example.flikrphotosearch.photo.presentation.components.SearchFieldView
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

@Composable
internal fun HomeScreen(
    viewModel: MainViewModel,
) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    ContentScreen(
        isLoading = viewState.isLoading,
    ) { paddingValues ->
        Content(
            searchQuery = viewState.searchQuery,
            searchHistory = viewState.searchHistory,
            hasError = viewState.error != null,
            onEventSend = { viewModel.setEvent(it) },
            paddingValues = paddingValues,
        )
    }
}

@Composable
private fun Content(
    searchQuery: String,
    searchHistory: List<String>,
    hasError: Boolean,
    onEventSend: (MainUiEvent) -> Unit,
    paddingValues: PaddingValues,
) {
    Column(
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .fillMaxSize()
            .padding(
                paddingValues = PaddingValues(
                    bottom = 0.dp,
                    start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                    end = paddingValues.calculateEndPadding(LayoutDirection.Ltr)
                )
            )
    ) {
        ContentTitle(
            modifier = Modifier
                .fillMaxWidth(),
            title = stringResource(Res.string.home_screen_title),
        )

        SearchFieldView(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = SPACING_LARGE.dp),
            searchInputPrefilledText = searchQuery,
            searchHistory = searchHistory,
            label = stringResource(Res.string.home_screen_search_field_label),
            buttonText = stringResource(Res.string.home_screen_search_button_text),
            searchErrorReceived = hasError,
            doOnSearchRequest = { text ->
                onEventSend(
                    MainUiEvent.RequestSearch(
                        searchQuery = text,
                        BottomBarScreen.Home
                    )
                )
            },
            doOnSearchHistoryDropDownItemClick = { text ->
                onEventSend(
                    MainUiEvent.OnSearchHistoryItemSelected(
                        searchQuery = text,
                        BottomBarScreen.Home
                    )
                )
            },
            doOnSearchTextChange = { text -> onEventSend(MainUiEvent.OnSearchQueryChange(text)) },
            doOnClearHistoryClick = { index ->
                onEventSend(MainUiEvent.RemoveSearchHistory(index))
            }
        )
    }
}

@Preview
@Composable
private fun HomePreview(
    @PreviewParameter(HomePreviewParameterProvider::class) viewState: MainUiState,
) {
    FlickrPhotoSearchTheme {
        ContentScreen(
            isLoading = viewState.isLoading,
        ) {
            Content(
                searchQuery = viewState.searchQuery,
                searchHistory = viewState.searchHistory,
                hasError = viewState.error != null,
                onEventSend = {},
                paddingValues = PaddingValues(1.dp)
            )
        }
    }
}


private class HomePreviewParameterProvider : PreviewParameterProvider<MainUiState> {
    val viewState = MainUiState()

    override val values = sequenceOf(
        viewState,
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
                    title = "Photo Two",
                    url = "url",
                    isPublic = true,
                    isFriend = true,
                    isFamily = true
                )
            ),
        ),
    )
}
