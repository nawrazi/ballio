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

	@GetMapping("/{id}")
	fun getStandings(@PathVariable id: Int): StandingsDto {

		var standings = standingsService.getStandingsById(id)

		if (standings == null || standings.isOutdated)
			standings = standingsService.updateStandings(StandingsScraper(League.getById(id)).scrape())

		return standings.toDto()
	}

}
