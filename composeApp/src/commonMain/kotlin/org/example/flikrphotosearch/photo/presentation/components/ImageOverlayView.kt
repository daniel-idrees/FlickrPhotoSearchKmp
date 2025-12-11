package org.example.flikrphotosearch.photo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.example.flikrphotosearch.core.presentation.SPACING_MEDIUM
import org.example.flikrphotosearch.core.presentation.SPACING_SMALL

@Composable
internal fun ImageOverlayView(
    imageUrl: String,
    onClose: () -> Unit,
) {

    Box(
        modifier = Modifier.pointerInput(Unit) {
            // Consume all pointer events to block clicks behind the overlay
            awaitPointerEventScope {
                while (true) {
                    awaitPointerEvent()
                }
            }
        }
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.9f))
    ) {

        ClearIcon(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(SPACING_MEDIUM.dp)
                .clickable { onClose() },
            tint = Color.White
        )

        AsyncImage(
            model = imageUrl,
            contentDescription = "Searched photo on overlay",
            modifier = Modifier
                .align(Alignment.Center)
                .padding(SPACING_SMALL.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
    }
}
