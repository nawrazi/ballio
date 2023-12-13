package nazrawi.ballio.scraper

import it.skrape.core.htmlDocument
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape
import it.skrape.selects.html5.a
import it.skrape.selects.html5.b
import it.skrape.selects.html5.img
import it.skrape.selects.html5.td
import nazrawi.ballio.entitiy.League
import nazrawi.ballio.entitiy.Standings
import nazrawi.ballio.entitiy.Team

class WebScraper(private val league: League) {

    private val websiteUrl = "https://www.theguardian.com/football/${league.slug}/table"

    fun scrape(): Standings {
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
                    b {
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
                    val crests = mutableListOf<String>()
                    img {
                        withClass = "team-crest"
                        findAll {
                            this.forEach { crests.addAll(it.eachSrc) }
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
                                crest = crests[i],
                                leagueId = league.id,
                            )
                        )
                    }
                    Standings(leagueId = league.id, teams = teams)
                }
            }
        }
    }
}