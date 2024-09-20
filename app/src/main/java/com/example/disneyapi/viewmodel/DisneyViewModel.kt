package com.example.disneyapi.viewmodel

import DisneyCharacter
import androidx.compose.runtime.getValue

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import retrofit2.HttpException

sealed class DisneyUiState {
    object Loading : DisneyUiState()
    data class Success(val characters: List<DisneyCharacter>) : DisneyUiState()
    data class Error(val message: String) : DisneyUiState()
}




class DisneyViewModel : ViewModel() {
    private val _uiState = MutableStateFlow<DisneyUiState>(DisneyUiState.Loading)
    val uiState: StateFlow<DisneyUiState> = _uiState // Exponer como StateFlow

    init {
        getAllCharacters() // Llama a la función al inicializar
    }

    fun getAllCharacters() {
        viewModelScope.launch {
            _uiState.value = DisneyUiState.Loading // Muestra pantalla de carga
            try {
                val response = DisneyApi.retrofitService.getAllCharacters()
                _uiState.value = DisneyUiState.Success(response.data) // Asigna los datos
            } catch (e: IOException) {
                _uiState.value = DisneyUiState.Error("No internet connection") // Maneja IOException
            } catch (e: HttpException) {
                _uiState.value = DisneyUiState.Error("Error: ${e.message()}") // Maneja HttpException
            } catch (e: Exception) {
                _uiState.value = DisneyUiState.Error("Error: ${e.message}") // Maneja excepciones generales
            }
        }
    }
}
