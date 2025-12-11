package org.example.flikrphotosearch.photo.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.flikrphotosearch.core.presentation.SPACING_EXTRA_SMALL
import org.example.flikrphotosearch.core.presentation.SPACING_LARGE
import org.example.flikrphotosearch.core.presentation.SPACING_MEDIUM
import org.example.flikrphotosearch.core.presentation.SPACING_SMALL
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun SearchHistoryItem(
    modifier: Modifier = Modifier,
    searchText: String,
    onItemClick: () -> Unit = {},
    onDeleteIconClick: () -> Unit = {},
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(SPACING_MEDIUM.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier
                .weight(0.90f)
                .clickable {
                    onItemClick()
                },
            shape = RoundedCornerShape(SPACING_SMALL.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = SPACING_EXTRA_SMALL.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = SPACING_SMALL.dp,
                        vertical = SPACING_EXTRA_SMALL.dp
                    ),
                horizontalArrangement = Arrangement.spacedBy(SPACING_MEDIUM.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                PastIconImageView(modifier = Modifier.size(SPACING_LARGE.dp))

                TextBodyMedium(
                    text = searchText,
                )
            }
        }

        DeleteIcon(
            modifier = Modifier.size(SPACING_LARGE.dp)
                .weight(0.10f)
                .clickable {
                    onDeleteIconClick()
                }
        )
    }
}

@Composable
@Preview
private fun SearchHistoryItemPreview(){
    SearchHistoryItem(searchText = "some text")
}