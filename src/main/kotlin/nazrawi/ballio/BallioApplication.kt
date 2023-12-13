package nazrawi.ballio

import nazrawi.ballio.entitiy.League
import nazrawi.ballio.entitiy.StandingsDto
import nazrawi.ballio.scraper.StandingsScraper
import nazrawi.ballio.service.StandingsService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class BallioApplication

fun main(args: Array<String>) {
	runApplication<BallioApplication>(*args)
}

@RestController
@RequestMapping("/standings")
class StandingsController(private val standingsService: StandingsService) {

	@GetMapping("/{slug}")
	fun getStandings(@PathVariable slug: String): StandingsDto {
		val league = League.getBySlug(slug)
		var standings = standingsService.getStandingsByLeagueId(league.id)

		if (standings == null || standings.isOutdated)
			standings = standingsService.updateStandings(StandingsScraper(league).scrape())

		return standings.toDto()
	}

}
