package nazrawi.ballio.repository

import nazrawi.ballio.entitiy.Standings
import org.springframework.data.repository.CrudRepository

interface StandingsRepository : CrudRepository<Standings, Long> {
    fun findByLeagueId(leagueId: Int): Standings?
    fun deleteByLeagueId(leagueId: Int)
}
