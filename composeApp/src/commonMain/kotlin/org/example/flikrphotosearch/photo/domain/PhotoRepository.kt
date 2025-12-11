package org.example.flikrphotosearch.photo.domain

import org.example.flikrphotosearch.core.domain.DataError
import org.example.flikrphotosearch.core.domain.Result

interface PhotoRepository {
    suspend fun searchPhotos(searchText: String): Result<List<Photo>, DataError.Remote>
}