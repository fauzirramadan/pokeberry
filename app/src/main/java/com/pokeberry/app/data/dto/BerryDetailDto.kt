package com.pokeberry.app.data.dto

data class BerryDetailDto(
    val id: Int,
    val name: String,
    val growth_time: Int,
    val max_harvest: Int,
    val size: Int,
    val smoothness: Int,
    val soil_dryness: Int,
    val item: ItemReferenceDto
)

data class ItemReferenceDto(
    val name: String,
    val url: String
)