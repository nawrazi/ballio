package nazrawi.ballio.controller

import nazrawi.ballio.entitiy.League
import nazrawi.ballio.entitiy.StandingsDto
import nazrawi.ballio.scraper.StandingsScraper
import nazrawi.ballio.service.StandingsService
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
@RequestMapping("/")
class StandingsController(private val standingsService: StandingsService) {

    @GetMapping("/api/standings/{slug}")
    fun getStandingsApi(@PathVariable slug: String): StandingsDto {
        val league = League.getBySlug(slug)
        var standings = standingsService.getStandingsByLeagueId(league.id)

        if (standings == null || standings.isOutdated)
            standings = standingsService.updateStandings(StandingsScraper(league).scrape())

        return standings.toDto()
    }

    @GetMapping("/standings/{slug}")
    fun getStandings(@PathVariable slug: String, model: Model): ModelAndView {
        val league = League.getBySlug(slug)
        var standings = standingsService.getStandingsByLeagueId(league.id)

        if (standings == null || standings.isOutdated)
            standings = standingsService.updateStandings(StandingsScraper(league).scrape())

        model.apply {
            addAttribute("leagueName", league.displayName)
            addAttribute("standings", standings)
        }

        return ModelAndView().apply { viewName = "standings" }
    }

}
