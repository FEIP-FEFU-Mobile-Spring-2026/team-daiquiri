package fefu.storeProject.data.api

import retrofit2.http.GET

interface CatalogApiService {
    @GET("catalog")
    suspend fun getCatalog(): CatalogResponse
}
