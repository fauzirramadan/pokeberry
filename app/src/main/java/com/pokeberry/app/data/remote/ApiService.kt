package com.pokeberry.app.data.remote

import com.pokeberry.app.data.dto.BerryResponseDto
import com.pokeberry.app.data.dto.ItemDetailDto
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface ApiService {

    @GET("berry")
    suspend fun getBerries(): BerryResponseDto

    @GET
    suspend fun getBerryListByUrl(
        @Url url: String
    ): BerryResponseDto

    @GET("berry/{id}")
    suspend fun getBerryDetail(
        @Path("id") id: Int
    ): Response<ResponseBody>

    @GET
    suspend fun getItemDetail(
        @Url url: String
    ): ItemDetailDto
}