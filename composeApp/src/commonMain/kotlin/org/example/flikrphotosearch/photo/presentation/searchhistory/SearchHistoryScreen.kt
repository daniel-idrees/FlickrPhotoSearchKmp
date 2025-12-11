package org.example.flikrphotosearch.photo.presentation.searchhistory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
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
import flickrphotosearch.composeapp.generated.resources.search_history_screen_title
import org.example.flikrphotosearch.app.config.BottomBarScreen
import org.example.flikrphotosearch.app.theme.FlickrPhotoSearchTheme
import org.example.flikrphotosearch.core.presentation.SPACING_MEDIUM
import org.example.flikrphotosearch.core.presentation.content.ContentScreen
import org.example.flikrphotosearch.core.presentation.content.ContentTitle
import org.example.flikrphotosearch.photo.domain.Photo
import org.example.flikrphotosearch.photo.presentation.MainUiEvent
import org.example.flikrphotosearch.photo.presentation.MainUiState
import org.example.flikrphotosearch.photo.presentation.MainViewModel
import org.example.flikrphotosearch.photo.presentation.components.SearchHistoryListView
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.ui.tooling.preview.PreviewParameter
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider

@Composable
internal fun SearchHistoryScreen(viewModel: MainViewModel) {
    val viewState by viewModel.viewState.collectAsStateWithLifecycle()

    ContentScreen(
        isLoading = viewState.isLoading,
    ) { paddingValues ->
        Content(
            searchHistory = viewState.searchHistory,
            onEventSend = { viewModel.setEvent(it) },
            paddingValues = paddingValues
        )
    }
}

@Composable
private fun Content(
    searchHistory: List<String>,
    onEventSend: (MainUiEvent) -> Unit,
    paddingValues: PaddingValues,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(SPACING_MEDIUM.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(
                paddingValues = PaddingValues(
                    start = paddingValues.calculateStartPadding(LayoutDirection.Ltr),
                    end = paddingValues.calculateEndPadding(LayoutDirection.Ltr)
                )
            )
    ) {
        ContentTitle(
            modifier = Modifier
                .fillMaxWidth(),
            title = stringResource(Res.string.search_history_screen_title),
        )

        SearchHistoryListView(
            modifier = Modifier.fillMaxHeight(),
            searchHistory = searchHistory,
            fromScreen = BottomBarScreen.History,
            onEventSend = onEventSend
        )
    }
}

@Preview
@Composable
private fun SearchHistoryPreview(
    @PreviewParameter(SearchHistoryPreviewParameterProvider::class) viewState: MainUiState,
) {
    FlickrPhotoSearchTheme {
        ContentScreen(
            isLoading = viewState.isLoading,
        ) {
            Content(
                searchHistory = viewState.searchHistory,
                onEventSend = {},
                paddingValues = PaddingValues(1.dp),
            )
        }
    }
}


private class SearchHistoryPreviewParameterProvider : PreviewParameterProvider<MainUiState> {
    val viewState = MainUiState(
        photoList = emptyList(),
        searchHistory = emptyList(),
        isLoading = false,
        error = null,
        searchQuery = "",
        lastSearch = "",
    )

    override val values = sequenceOf(
        viewState,
        viewState.copy(
            searchQuery = "query",
            photoList = listOf(
                Photo(
                    title = "Photo One",
                    url = "url",
                    isPublic = false,
                    isFriend = false,
                    isFamily = false
                )
            ),
            searchHistory = listOf("test1", "test2"),
        ),
    )
}
