package nazrawi.ballio.scraper

import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.eachSrc
import it.skrape.selects.eachText
import it.skrape.selects.html5.a
import it.skrape.selects.html5.b
import it.skrape.selects.html5.img
import it.skrape.selects.html5.td
import nazrawi.ballio.entitiy.League
import nazrawi.ballio.entitiy.Standings
import nazrawi.ballio.entitiy.Team

class StandingsScraper(private val league: League) {

    private val websiteUrl = "https://www.theguardian.com/football/${league.sourceUri}/table"

    fun scrape(): Standings = skrape(HttpFetcher) {
        request { url = websiteUrl }
        response {
            htmlDocument(this.responseBody) {
                val names = a {
                    withClass = "team-name__long"
                    findAll { eachText }
                }
                val ranks = td {
                    withClass = "table-column--sub"
                    findAll { eachText }
                }
                val points = b {
                    findAll { eachText }
                }
                val gds = td {
                    withClass = "table-column--importance-3"
                    findAll { eachText }
                }
                val crests = img {
                    withClass = "team-crest"
                    findAll { eachSrc }
                }
                val teams = names.indices.map {
                    Team(
                        name = names[it],
                        ranking = ranks[it].toInt(),
                        points = points[it].toInt(),
                        gd = gds[it].toInt(),
                        crest = crests[it],
                        leagueId = league.id
                    )
                }
                Standings(leagueId = league.id, teams = teams)
            }
        }
    }
}
