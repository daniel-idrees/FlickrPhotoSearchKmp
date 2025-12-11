package org.example.flikrphotosearch.photo.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PhotoDto(
    @SerialName("id") val id: String,
    @SerialName("owner") val owner: String,
    @SerialName("secret") val secret: String,
    @SerialName("server") val server: String,
    @SerialName("farm") val farm: Long,
    @SerialName("title") val title: String,
    @SerialName("ispublic") val isPublic: Int,
    @SerialName("isfriend") val isFriend: Int,
    @SerialName("isfamily") val isFamily: Int
)