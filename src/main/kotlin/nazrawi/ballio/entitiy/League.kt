package nazrawi.ballio.entitiy

enum class League(val slug: String, val id: Int) {
    PremierLeague("premierleague", 1);

    companion object {
        fun getById(id: Int): League {
            return entries.find { it.id == id } ?: PremierLeague
        }
    }
}