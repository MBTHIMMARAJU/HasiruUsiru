package com.yourname.hasiruusiru

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TreeViewModel : ViewModel() {
    private val repository = TreeRepository()

    private val _trees = MutableStateFlow<List<Tree>>(emptyList())
    val trees: StateFlow<List<Tree>> = _trees.asStateFlow()

    private val _speciesGuide = MutableStateFlow<List<SpeciesInfo>>(emptyList())
    val speciesGuide: StateFlow<List<SpeciesInfo>> = _speciesGuide.asStateFlow()

    private val _streetLeaderboard = MutableStateFlow<List<StreetScore>>(emptyList())
    val streetLeaderboard: StateFlow<List<StreetScore>> = _streetLeaderboard.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getTreesFlow().collect {
                _trees.value = it
            }
        }
        fetchSpeciesGuide()
        fetchLeaderboard()
    }

    private fun fetchSpeciesGuide() {
        viewModelScope.launch {
            _speciesGuide.value = repository.getSpeciesGuide()
        }
    }

    private fun fetchLeaderboard() {
        viewModelScope.launch {
            _streetLeaderboard.value = repository.getStreetLeaderboard()
        }
    }

    fun tagTree(tree: Tree) {
        viewModelScope.launch {
            repository.tagTree(tree)
        }
    }

    // AI Features (Gemini)
    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = "YOUR_API_KEY" // User needs to provide this or I use a placeholder
    )

    suspend fun analyzeTreeHealth(imageBase64: String): String {
        return try {
            val response = generativeModel.generateContent(
                content {
                    text("Analyze this tree health and return JSON with health (Good/Average/Poor) and reasons.")
                    // image(bitmap) would go here in a real implementation with valid bitmap
                }
            )
            response.text ?: "Could not analyze health."
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
    }
}
