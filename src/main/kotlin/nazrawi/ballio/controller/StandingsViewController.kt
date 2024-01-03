package nazrawi.ballio.controller

import nazrawi.ballio.entitiy.League
import nazrawi.ballio.scraper.StandingsScraper
import nazrawi.ballio.service.StandingsService
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.ModelAndView

@RestController
@RequestMapping("/standings")
class StandingsViewController(private val standingsService: StandingsService) {

    @GetMapping("/")
    fun getAllStandings(model: Model): ModelAndView {
        model.apply {
            addAttribute("leagues", standingsService.getAllStandings())
        }

        return ModelAndView().apply { viewName = "leagues" }
    }

    @GetMapping("/{slug}")
    fun getStandingsViewByLeagueSlug(@PathVariable slug: String, model: Model): ModelAndView {
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
