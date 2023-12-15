package nazrawi.ballio.repository

import jakarta.transaction.Transactional
import nazrawi.ballio.entitiy.Standings
import org.springframework.data.repository.CrudRepository

@Transactional
interface StandingsRepository : CrudRepository<Standings, Long> {
    fun findByLeagueId(leagueId: Int): Standings?
    fun deleteByLeagueId(leagueId: Int)
}
