package org.example.flikrphotosearch.photo.data.network

import org.example.flikrphotosearch.core.domain.DataError
import org.example.flikrphotosearch.core.domain.Result
import org.example.flikrphotosearch.photo.data.dto.PhotoSearchResponseDto

interface RemotePhotoDataSource {
    suspend fun getPhotos(searchText: String): Result<PhotoSearchResponseDto, DataError.Remote>
}