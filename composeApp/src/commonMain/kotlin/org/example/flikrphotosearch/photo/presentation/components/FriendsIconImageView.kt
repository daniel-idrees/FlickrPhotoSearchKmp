@file:OptIn(ExperimentalMaterial3Api::class)

package org.example.flikrphotosearch.photo.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipBox
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.rememberTooltipState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import flickrphotosearch.composeapp.generated.resources.Res
import flickrphotosearch.composeapp.generated.resources.ic_friends
import flickrphotosearch.composeapp.generated.resources.search_screen_result_photo_friend_icon_tooltip_text
import org.example.flikrphotosearch.core.presentation.SPACING_EXTRA_SMALL
import org.example.flikrphotosearch.core.presentation.SPACING_SMALL
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun FriendsIconImageView(modifier: Modifier = Modifier) {

    val tooltipState = rememberTooltipState()

    TooltipBox(
        positionProvider = TooltipDefaults.rememberPlainTooltipPositionProvider(),
        tooltip = {
            Surface(
                modifier = Modifier.padding(top = SPACING_SMALL.dp),
                shape = RoundedCornerShape(SPACING_EXTRA_SMALL.dp),
            ) {
                Text(
                    text = stringResource(Res.string.search_screen_result_photo_friend_icon_tooltip_text),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        },
        state = tooltipState
    ) {
        Image(
            modifier = modifier,
            painter = painterResource(Res.drawable.ic_friends),
            contentDescription = "Photo friend visibility icon"
        )
    }
}