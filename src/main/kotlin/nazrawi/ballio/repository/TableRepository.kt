package nazrawi.ballio.repository

import nazrawi.ballio.entitiy.Table
import org.springframework.data.repository.CrudRepository

interface TableRepository : CrudRepository<Table, Long> {
    fun findByLeagueId(leagueId: Int): Table?
    fun deleteByLeagueId(leagueId: Int)
}
