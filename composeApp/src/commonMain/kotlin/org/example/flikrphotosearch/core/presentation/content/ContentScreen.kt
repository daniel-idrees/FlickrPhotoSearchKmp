package org.example.flikrphotosearch.core.presentation.content

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.example.flikrphotosearch.core.presentation.screenPaddings
import org.example.flikrphotosearch.core.presentation.view.LoadingView

@Composable
internal fun ContentScreen(
    isLoading: Boolean = false,
    bodyContent: @Composable (PaddingValues) -> Unit,
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        Scaffold { innerPadding ->
            if (isLoading) {
                LoadingView(modifier = Modifier.fillMaxSize())
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.weight(1f)) {
                        bodyContent(screenPaddings(innerPadding))
                    }
                }
            }
        }
    }
}