package com.yourname.hasiruusiru

import androidx.compose.ui.graphics.Color
import com.yourname.hasiruusiru.ui.theme.HealthyGreen
import com.yourname.hasiruusiru.ui.theme.AverageYellow
import com.yourname.hasiruusiru.ui.theme.PoorRed
import java.util.Date

data class Tree(
    val id: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val species: String = "Neem",
    val kannadaName: String = "ಬೇವು",
    val health: TreeHealth = TreeHealth.GOOD,
    val girthCm: Double = 0.0,
    val oxygenScore: Double = 0.0,
    val photoUrl: String? = null,
    val taggedBy: String = "",
    val taggedAt: Date = Date(),
    val streetName: String = "",
    val ward: String = "",
    val verified: Boolean = false,
    val aiHealthScore: Double = 0.0
)

enum class TreeHealth(val label: String, val color: Color) {
    GOOD("Good", HealthyGreen),
    AVERAGE("Average", AverageYellow),
    POOR("Poor", PoorRed)
}

data class UserProfile(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val totalTreesTagged: Int = 0,
    val oxygenContribution: Double = 0.0,
    val badges: List<String> = emptyList(),
    val rank: Int = 0
)

data class SpeciesInfo(
    val englishName: String,
    val kannadaName: String,
    val scientificName: String,
    val oxygenFactor: Double,
    val description: String,
    val photoUrl: String,
    val nativeToKarnataka: Boolean = true
)

data class StreetScore(
    val streetName: String,
    val ward: String,
    val totalTrees: Int,
    val oxygenScore: Int,
    val greenGaps: Int
)
