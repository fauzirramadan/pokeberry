package com.pokeberry.app.data.dto

data class BerryResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<BerryDto>
)