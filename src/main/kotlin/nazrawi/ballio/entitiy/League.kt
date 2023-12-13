package nazrawi.ballio.entitiy

enum class League(val id: Int, val slug: String, val sourceUri: String) {
    PremierLeague(1, "premier-league", "premierleague");

    companion object {
        fun getBySlug(slug: String): League {
            return entries.find { it.slug == slug } ?: throw Exception("Invalid League")
        }
    }
}
