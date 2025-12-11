package org.example.flikrphotosearch.photo.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoSearchDetailDto(
    @SerialName("page") val page: Int,
    @SerialName("pages") val pages: Int,
    @SerialName("perpage") val perPage: Int,
    @SerialName("total") val total: Int,
    @SerialName("photo") val photos: List<PhotoDto>
)
