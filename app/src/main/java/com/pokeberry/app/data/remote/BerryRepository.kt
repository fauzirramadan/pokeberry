package com.pokeberry.app.data.remote

import com.google.gson.Gson
import com.pokeberry.app.data.mapper.toDomain
import com.pokeberry.app.data.dto.BerryDetailDto
import com.pokeberry.app.data.dto.BerryResponseDto
import com.pokeberry.app.network.RetrofitClient
import com.pokeberry.app.domain.model.Berry
import com.pokeberry.app.domain.model.BerryDetail

class BerryRepository {

    private val apiService = RetrofitClient.apiService

    suspend fun getBerries(): List<Berry> {

        return apiService
            .getBerries()
            .results
            .map { it.toDomain() }
    }

    suspend fun getBerryListByUrl(
        url: String
    ): BerryResponseDto {


        val response = RetrofitClient
            .apiService
            .getBerryListByUrl(url)

        return response
    }

    suspend fun getBerryDetail(id: Int): Pair<BerryDetail, String> {

        val response = apiService.getBerryDetail(id)
        val rawJson = response.body()?.string() ?: ""
        val dto = Gson().fromJson(rawJson, BerryDetailDto::class.java)

        return Pair(dto.toDomain(), rawJson)
    }
}