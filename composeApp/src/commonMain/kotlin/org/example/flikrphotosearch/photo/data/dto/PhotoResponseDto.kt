package org.example.flikrphotosearch.photo.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoSearchResponseDto(
    @SerialName("photos") val photoSearchDetail: PhotoSearchDetailDto,
    @SerialName("stat") val status: String,
    @SerialName("code") val errorCode: Int? = null
)

