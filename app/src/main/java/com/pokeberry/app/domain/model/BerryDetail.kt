package com.pokeberry.app.domain.model

data class BerryDetail(
    val id: Int,
    val name: String,
    val growthTime: Int,
    val maxHarvest: Int,
    val size: Int,
    val smoothness: Int,
    val soilDryness: Int,
    val itemUrl: String?
)