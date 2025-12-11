package org.example.flikrphotosearch.core.presentation.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.example.flikrphotosearch.core.presentation.SPACING_MEDIUM
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun ContentTitle(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String? = null,
    titleStyle: TextStyle = MaterialTheme.typography.headlineSmall,
    subtitleStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    subTitleMaxLines: Int = Int.MAX_VALUE,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(SPACING_MEDIUM.dp, Alignment.Top)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = title,
            style = titleStyle,
        )

        subtitle?.let { safeSubtitle ->
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = safeSubtitle,
                style = subtitleStyle,
                maxLines = subTitleMaxLines,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun ContentTitlePreview() {
    ContentTitle(
        modifier = Modifier.fillMaxWidth(),
        title = "This is the title",
        subtitle = "This is the sub title"
    )
}

@Preview
@Composable
private fun ContentTitleNoSubtitlePreview() {
    ContentTitle(
        modifier = Modifier.fillMaxWidth(),
        title = "This is the title",
        subtitle = null
    )
}