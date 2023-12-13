package nazrawi.ballio

import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.html5.a
import it.skrape.selects.html5.td
import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class WebScraper(private val league: League) {

    private val websiteUrl = "https://www.theguardian.com/football/${league.slug}/table"

    fun scrape(): LeagueTable {
        return skrape(HttpFetcher) {
            request { url = websiteUrl }
            response {
                htmlDocument(this.responseBody) {
                    val names = mutableListOf<String>()
                    a {
                        withClass = "team-name__long"
                        findAll {
                            this.forEach { names.add(it.text) }
                        }
                    }
                    val ranks = mutableListOf<String>()
                    td {
                        withClass = "table-column--sub"
                        findAll {
                            this.forEach { ranks.add(it.text) }
                        }
                    }
                    val points = mutableListOf<String>()
                    "b" {
                        findAll {
                            this.forEach { points.add(it.text) }
                        }
                    }
                    val gd = mutableListOf<String>()
                    td {
                        withClass = "table-column--importance-3"
                        findAll {
                            this.forEach { gd.add(it.text) }
                        }
                    }
                    val teams = mutableListOf<Team>()
                    for (i in 0..< names.size) {
                        teams.add(
                            Team(
                                name = names[i],
                                rank = ranks[i].toInt(),
                                points = points[i].toInt(),
                                gd = gd[i].toInt(),
                                leagueId = league.id,
                            )
                        )
                    }
                    LeagueTable(leagueId = league.id, teams = teams)
                }
            }
        }
    }
}

@Entity
class LeagueTable(
    var leagueId: Int,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "leagueId")
    var teams: List<Team>,
    var updatedAt: String = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
) {
    val isAtLeastOneDayOld: Boolean
        get() = LocalDateTime
                .parse(updatedAt, DateTimeFormatter.ISO_DATE_TIME)
                .isBefore(LocalDateTime.now().minusDays(1))

    fun toDto() = LeagueTableDto(
        leagueId = leagueId,
        teams = teams.map { it.toDto() },
        updatedAt = updatedAt,
        id = id,
    )
}

@Entity
class Team(
    var name: String,
    var rank: Int,
    var points: Int,
    var gd: Int,
    var leagueId: Int,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
) {
    fun toDto() = TeamDto(
        name = name,
        rank = rank,
        points = points,
        gd = gd,
        leagueId = leagueId,
        id = id,
    )
}

data class LeagueTableDto(
        val leagueId: Int,
        val teams: List<TeamDto>,
        val updatedAt: String,
        val id: Long
)

data class TeamDto(
        val name: String,
        val rank: Int,
        val points: Int,
        val gd: Int,
        val leagueId: Int,
        val id: Long,
)

enum class League(val slug: String, val id: Int) {
    PremierLeague("premierleague", 1);

    companion object {
        fun getById(id: Int): League {
            return entries.find { it.id == id } ?: PremierLeague
        }
    }
}
