package com.pokeberry.app.data.mapper

import com.pokeberry.app.data.dto.BerryDetailDto
import com.pokeberry.app.data.dto.BerryDto
import com.pokeberry.app.domain.model.Berry
import com.pokeberry.app.domain.model.BerryDetail

fun BerryDto.toDomain(): Berry {

    val id = url
        .trimEnd('/')
        .split("/")
        .last()
        .toInt()

    return Berry(
        id = id,
        name = name,
        url = url
    )
}
fun BerryDetailDto.toDomain(): BerryDetail {
    return BerryDetail(
        id = id,
        name = name,
        growthTime = growth_time,
        maxHarvest = max_harvest,
        size = size,
        smoothness = smoothness,
        soilDryness = soil_dryness,
        itemUrl = item.url
    )
}