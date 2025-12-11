package org.example.flikrphotosearch.core.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun LoadingView(modifier: Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

@Preview
@Composable
private fun LoadingViewPreview() {
    LoadingView(Modifier.fillMaxSize())
}