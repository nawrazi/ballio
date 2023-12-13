package nazrawi.ballio.service

import nazrawi.ballio.entitiy.Standings
import nazrawi.ballio.repository.StandingsRepository
import org.springframework.stereotype.Service

@Service
class StandingsService(private val standingsRepository: StandingsRepository) {

    fun getStandingsById(id: Int) = standingsRepository.findByLeagueId(id)

    fun updateStandings(standings: Standings): Standings {
        standingsRepository.deleteByLeagueId(standings.leagueId)
        return standingsRepository.save(standings)
    }

}
