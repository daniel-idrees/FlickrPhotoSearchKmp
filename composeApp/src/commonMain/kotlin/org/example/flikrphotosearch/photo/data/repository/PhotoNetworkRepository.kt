package org.example.flikrphotosearch.photo.data.repository

import org.example.flikrphotosearch.core.domain.DataError
import org.example.flikrphotosearch.core.domain.Result
import org.example.flikrphotosearch.core.domain.map
import org.example.flikrphotosearch.photo.data.mappers.toPhotoList
import org.example.flikrphotosearch.photo.data.network.RemotePhotoDataSource
import org.example.flikrphotosearch.photo.domain.Photo
import org.example.flikrphotosearch.photo.domain.PhotoRepository

class PhotoNetworkRepository(
    private val photoDataSource: RemotePhotoDataSource,
) : PhotoRepository {
    override suspend fun searchPhotos(searchText: String): Result<List<Photo>, DataError.Remote> {
        return photoDataSource
            .getPhotos(searchText)
            .map { dto ->
                dto.photoSearchDetail.photos.toPhotoList()
            }
    }
}