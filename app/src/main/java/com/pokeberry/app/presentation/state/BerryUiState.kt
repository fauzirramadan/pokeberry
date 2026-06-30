package com.pokeberry.app.presentation.state

import com.pokeberry.app.domain.model.Berry

data class BerryUiState(

    val isLoading: Boolean = false,

    val berries: List<Berry> = emptyList(),

    val error: String? = null
)