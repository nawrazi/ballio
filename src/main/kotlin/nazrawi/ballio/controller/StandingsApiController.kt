package nazrawi.ballio.controller

import nazrawi.ballio.entitiy.League
import nazrawi.ballio.entitiy.StandingsDto
import nazrawi.ballio.scraper.StandingsScraper
import nazrawi.ballio.service.StandingsService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/standings")
class StandingsApiController(private val standingsService: StandingsService) {

    @GetMapping("/{slug}")
    fun getStandingsByLeagueSlug(@PathVariable slug: String): StandingsDto {
        val league = League.getBySlug(slug)
        var standings = standingsService.getStandingsByLeagueId(league.id)

        if (standings == null || standings.isOutdated)
            standings = standingsService.updateStandings(StandingsScraper(league).scrape())

        return standings.toDto()
    }

}