package com.pokeberry.app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.pokeberry.app.data.mapper.toDomain
import com.pokeberry.app.data.remote.BerryRepository
import com.pokeberry.app.domain.model.Berry
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BerryViewModel : ViewModel() {

    private val repository = BerryRepository()

    private val gson = Gson()

    private val _lastRequestUrl =
        MutableStateFlow("")

    val lastRequestUrl: StateFlow<String> =
        _lastRequestUrl

    private val _lastResponse =
        MutableStateFlow("")

    val lastResponse: StateFlow<String> =
        _lastResponse

    private val _berries = MutableStateFlow<List<Berry>>(emptyList())
    val berries: StateFlow<List<Berry>> = _berries

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var nextUrl: String? =
        "https://pokeapi.co/api/v2/berry/"

    init {
        fetchBerryList()
    }

    fun fetchBerryList() {

        if (_isLoading.value) return

        if (nextUrl == null) return

        viewModelScope.launch {

            try {

                _isLoading.value = true

                val currentUrl = nextUrl!!

                _lastRequestUrl.value = currentUrl

                val response = repository
                    .getBerryListByUrl(currentUrl)

                _lastResponse.value =
                    gson.toJson(response)

                val newList = response.results.map {
                    it.toDomain()
                }

                _berries.value =
                    _berries.value + newList

                nextUrl = response.next

            } catch (e: Exception) {

                e.printStackTrace()

            } finally {

                _isLoading.value = false
            }
        }
    }

    fun refreshBerryList() {

        nextUrl = "https://pokeapi.co/api/v2/berry/"

        _berries.value = emptyList()

        fetchBerryList()
    }
}

