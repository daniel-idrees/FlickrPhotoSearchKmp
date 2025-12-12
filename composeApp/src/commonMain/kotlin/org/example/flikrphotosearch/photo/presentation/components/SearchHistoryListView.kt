package org.example.flikrphotosearch.photo.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import flickrphotosearch.composeapp.generated.resources.Res
import flickrphotosearch.composeapp.generated.resources.search_history_clear_all_button_text
import flickrphotosearch.composeapp.generated.resources.search_history_sub_title_if_no_history
import org.example.flikrphotosearch.app.config.BottomBarScreen
import org.example.flikrphotosearch.core.presentation.SPACING_EXTRA_SMALL
import org.example.flikrphotosearch.core.presentation.SPACING_MEDIUM
import org.example.flikrphotosearch.core.presentation.SPACING_SMALL
import org.example.flikrphotosearch.photo.presentation.MainUiAction
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun SearchHistoryListView(
    modifier: Modifier = Modifier,
    searchHistory: List<String>,
    fromScreen: BottomBarScreen,
    onEventSend: (MainUiAction) -> Unit,
) {

    if (searchHistory.isEmpty()) {
        TextBodyMedium(
            modifier.fillMaxWidth(),
            text = stringResource(Res.string.search_history_sub_title_if_no_history)
        )
        return
    }
    
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(SPACING_MEDIUM.dp)
    ) {
        ButtonWithTextView(
            modifier = Modifier
                .padding(horizontal = SPACING_EXTRA_SMALL.dp)
                .fillMaxWidth(),
            buttonText = stringResource(Res.string.search_history_clear_all_button_text),
            onClick = {
                onEventSend(MainUiAction.ClearSearchHistory)
            }
        )

        LazyColumn(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(SPACING_MEDIUM.dp),
        ) {

            items(searchHistory.size) { index ->
                val searchText = searchHistory.get(index)
                SearchHistoryItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = SPACING_SMALL.dp,
                            vertical = SPACING_EXTRA_SMALL.dp
                        ),
                    searchText = searchText,
                    onItemClick = {
                        onEventSend(
                            MainUiAction.OnSearchHistoryItemSelected(
                                searchText,
                                fromScreen
                            )
                        )
                    },
                    onDeleteIconClick = { onEventSend(MainUiAction.RemoveSearchHistory(index)) }
                )
            }
        }
    }
}

@Composable
@Preview
private fun SearchHistoryListViewPreview() {
    SearchHistoryListView(
        searchHistory = listOf("text1", "text2"),
        fromScreen = BottomBarScreen.History,
        onEventSend = {}
    )
}

@Composable
@Preview
private fun SearchHistoryListViewEmptyPreview() {
    SearchHistoryListView(
        searchHistory = emptyList(),
        fromScreen = BottomBarScreen.History,
        onEventSend = {}
    )
}

