package org.example.flikrphotosearch.photo.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import flickrphotosearch.composeapp.generated.resources.Res
import flickrphotosearch.composeapp.generated.resources.ic_past
import org.jetbrains.compose.resources.painterResource

@Composable
internal fun PastIconImageView(modifier: Modifier) {
    Image(
        modifier = modifier,
        painter = painterResource(Res.drawable.ic_past),
        contentDescription = "Past icon",
    )
}