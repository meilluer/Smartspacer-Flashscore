package com.meilluer.smartspacerFlashScore

import java.io.Serializable

data class MatchState(
    val id: String, // e.g. "Real Madrid - Barcelona"
    var title: String = "",
    var subtitle: String = "",
    var homeTeam: String = "Home Team",
    var awayTeam: String = "Away Team",
    var homeScore: String = "0",
    var awayScore: String = "0",
    var extras: String = "Match started",
    var scorers: String = "No goal scorers yet",
    var flag: Boolean = true, // Active match in progress
    var target_visibility: Boolean = true, // Controls visibility
    var lastUpdated: Long = System.currentTimeMillis()
) : Serializable

object MatchData {
    // Unique list/map of all live matches intercepted
    val activeMatches = LinkedHashMap<String, MatchState>()
    
    // Configurable display selector: "AUTO" (Most recent live) or specific matchId key
    var selectedMatchId: String = "AUTO"

    /**
     * Resolves the selected active match details for the UI cards and Smartspacer widget
     */
    fun getSelectedMatch(): MatchState? {
        if (selectedMatchId == "AUTO") {
            // Find the most recently updated active, visible match
            return activeMatches.values
                .filter { it.target_visibility }
                .maxByOrNull { it.lastUpdated }
        }
        return activeMatches[selectedMatchId]
    }

    fun reset() {
        activeMatches.clear()
        selectedMatchId = "AUTO"
    }
}
