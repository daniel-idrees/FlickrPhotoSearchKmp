package org.example.flikrphotosearch.photo.data.mappers

import org.example.flikrphotosearch.photo.data.dto.PhotoDto
import org.example.flikrphotosearch.photo.domain.Photo

fun List<PhotoDto>.toPhotoList(): List<Photo> =
    this.map { photoDto ->
        with(photoDto) {
            Photo(
                title = title,
                url = generatePhotoUrl(),
                isPublic = isPublic.toBoolean("isPublic"),
                isFriend = isFriend.toBoolean("isFriend"),
                isFamily = isFamily.toBoolean("isFamily")
            )
        }
    }

private fun PhotoDto.generatePhotoUrl(): String =
    "https://farm$farm.staticflickr.com/$server/${id}_$secret.jpg"

private fun Int.toBoolean(log: String): Boolean =
    when (this) {
        1 -> true
        0 -> false
        else -> throw IllegalArgumentException("$log value must be 0 or 1")
    }