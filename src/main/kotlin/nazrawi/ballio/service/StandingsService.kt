package nazrawi.ballio.service

import nazrawi.ballio.entitiy.League
import nazrawi.ballio.entitiy.LeagueDto
import nazrawi.ballio.entitiy.Standings
import nazrawi.ballio.repository.StandingsRepository
import org.springframework.stereotype.Service

@Service
class StandingsService(private val standingsRepository: StandingsRepository) {

    fun getAllStandings() = League.entries.map {
        LeagueDto(it.displayName, it.slug)
    }

    fun getStandingsByLeagueId(id: Int) = standingsRepository.findByLeagueId(id)

    fun updateStandings(standings: Standings): Standings {
        standingsRepository.deleteByLeagueId(standings.leagueId)
        return standingsRepository.save(standings)
    }

}
