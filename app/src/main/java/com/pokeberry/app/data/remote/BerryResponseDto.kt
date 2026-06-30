package com.pokeberry.app.data.remote

data class BerryResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<BerryDto>
)