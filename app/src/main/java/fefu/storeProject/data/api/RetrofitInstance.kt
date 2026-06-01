package fefu.storeProject.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://fefu2026spring.deploy.feip.dev/"
    private const val TOKEN = "Cmt7wdwFgDIi1_SRX8hlJIExs0jJKPr4axflLpExAxM"

    private val client = OkHttpClient.Builder()
        .addInterceptor { chain ->
            chain.proceed(
                chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $TOKEN")
                    .build()
            )
        }
        .build()

    val service: CatalogApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CatalogApiService::class.java)
}
