package nazrawi.ballio.entitiy

import nazrawi.ballio.exceptions.UnknownLeagueException

enum class League(val id: Int, val sourceUri: String) {
    PremierLeague(1, "premierleague");

    companion object {
        fun getBySlug(slug: String): League {
            return try {
                valueOf(slug.split("-").joinToString("") { it.capitalize() })
            } catch (e: Exception) {
                throw UnknownLeagueException()
            }
        }
    }
}
