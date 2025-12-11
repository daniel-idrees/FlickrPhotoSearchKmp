package org.example.flikrphotosearch.photo.data.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import org.example.flikrphotosearch.core.data.safeCall
import org.example.flikrphotosearch.core.domain.DataError
import org.example.flikrphotosearch.core.domain.Result
import org.example.flikrphotosearch.photo.data.dto.PhotoSearchResponseDto


class KtorRemotePhotoDataSource(
    private val client: HttpClient,
) : RemotePhotoDataSource {
    private val baseUrl: String = "https://api.flickr.com/"
    private val apiKey = "37ad288835e4c64fc0cb8af3f3a1a65d"

    override suspend fun getPhotos(searchText: String): Result<PhotoSearchResponseDto, DataError.Remote> {
        return safeCall<PhotoSearchResponseDto> {
            client.get("${baseUrl}services/rest/") {
                url {
                    parameters.append("text", searchText)
                    parameters.append("method", "flickr.photos.search")
                    parameters.append("api_key", apiKey)
                    parameters.append("format", "json")
                    parameters.append("nojsoncallback", "1")
                    parameters.append("safe_search", "1")
                }
            }.body()
        }
    }
}