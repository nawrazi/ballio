package nazrawi.ballio.entitiy

import nazrawi.ballio.exceptions.UnknownLeagueException

enum class League(val id: Int, val sourceUri: String) {
    PremierLeague(1, "premierleague"),
    LaLiga(2, "laligafootball"),
    SerieA(3, "serieafootball"),
    League1(4, "ligue1football"),
    Championship(5, "championship");

    companion object {
        fun getBySlug(slug: String): League {
            return try {
                valueOf(slug.split("-").joinToString("") { it.replaceFirstChar(Char::titlecase) })
            } catch (e: Exception) {
                throw UnknownLeagueException()
            }
        }
    }
}
