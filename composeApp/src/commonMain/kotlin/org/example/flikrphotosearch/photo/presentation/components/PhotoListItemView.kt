package org.example.flikrphotosearch.photo.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.room.util.TableInfo
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import coil3.request.ImageRequest
import flickrphotosearch.composeapp.generated.resources.Res
import flickrphotosearch.composeapp.generated.resources.ic_placeholder
import org.example.flikrphotosearch.app.theme.FlickrPhotoSearchTheme
import org.example.flikrphotosearch.core.presentation.SPACING_EXTRA_LARGE
import org.example.flikrphotosearch.core.presentation.SPACING_LARGE
import org.example.flikrphotosearch.core.presentation.SPACING_MEDIUM
import org.example.flikrphotosearch.core.presentation.SPACING_SMALL
import org.example.flikrphotosearch.photo.domain.Photo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun PhotoListItemView(
    modifier: Modifier = Modifier,
    photo: Photo,
    onPhotoClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .padding(bottom = SPACING_LARGE.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(SPACING_SMALL.dp)
        ) {
            AsyncImage(
                model = photo.url,
                contentDescription = "Searched photo",
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(SPACING_MEDIUM.dp))
                    .clickable { onPhotoClick() },
                contentScale = ContentScale.FillWidth,
                placeholder = painterResource(Res.drawable.ic_placeholder),
                error = painterResource(Res.drawable.ic_placeholder),
            )

            TextBodyMedium(
                text = photo.title,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val iconModifier = Modifier.size(SPACING_LARGE.dp)
                if (photo.isPublic) {
                    PublicIconImageView(modifier = iconModifier)
                } else {
                    PrivateIconImageView(modifier = iconModifier)
                }
                if (photo.isFriend) {
                    FriendsIconImageView(modifier = iconModifier)
                }
                if (photo.isFamily) {
                    FamilyIconImageView(modifier = iconModifier)
                }
            }

            Spacer(modifier = Modifier.height(SPACING_SMALL.dp))
            HorizontalDivider(
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(horizontal = SPACING_EXTRA_LARGE.dp)
            )
        }
    }
}

@Preview
@Composable
private fun PhotoListItemWithAllIconsPreview() {
    PhotoListItemView(
        photo = Photo(
            title = "Photo title",
            url = " https://farm66.staticflickr.com/65535/54375913088_62172768d8.jpg",
            isPublic = true,
            isFriend = true,
            isFamily = true
        ),
        onPhotoClick = { }
    )
}

@Preview
@Composable
private fun PhotoListItemWithFriendPreview() {
    FlickrPhotoSearchTheme {
        PhotoListItemView(
            photo = Photo(
                title = "Photo title",
                url = " https://farm66.staticflickr.com/65535/54375913088_62172768d8.jpg",
                isPublic = false,
                isFriend = true,
                isFamily = false
            ),
            onPhotoClick = { }
        )
    }
}