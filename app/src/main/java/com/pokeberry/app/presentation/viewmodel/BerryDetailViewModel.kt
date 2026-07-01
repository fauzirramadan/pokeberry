package com.pokeberry.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pokeberry.app.data.remote.BerryRepository
import com.pokeberry.app.domain.model.BerryDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BerryDetailViewModel : ViewModel() {

    private val repository = BerryRepository()

    private val _berry =
        MutableStateFlow<BerryDetail?>(null)

    val berry: StateFlow<BerryDetail?> =
        _berry

    private val _itemSpriteUrl =
        MutableStateFlow<String?>(null)

    val itemSpriteUrl: StateFlow<String?> =
        _itemSpriteUrl

    private val _isLoading =
        MutableStateFlow(false)

    val isLoading: StateFlow<Boolean> =
        _isLoading

    // REQUEST URL
    private val _lastRequestUrl =
        MutableStateFlow("")

    val lastRequestUrl: StateFlow<String> =
        _lastRequestUrl

    // RAW RESPONSE JSON
    private val _lastResponse =
        MutableStateFlow("")

    val lastResponse: StateFlow<String> =
        _lastResponse

    fun fetchBerryDetail(id: Int) {

        viewModelScope.launch {

            _isLoading.value = true

            try {

                _lastRequestUrl.value =
                    "https://pokeapi.co/api/v2/berry/$id/"

                val (response, rawJson) =
                    repository.getBerryDetail(id)

                _berry.value = response
                
                response.itemUrl?.let { url ->
                    val itemDetail = repository.getItemDetail(url)
                    _itemSpriteUrl.value = itemDetail.sprites.default
                }

                _lastResponse.value = rawJson

            } catch (e: Exception) {

                e.printStackTrace()

            } finally {

                _isLoading.value = false
            }
        }
    }
}