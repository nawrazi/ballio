package nazrawi.ballio

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Service

interface TableRepository : CrudRepository<LeagueTable, Long> {
    fun findByLeagueId(leagueId: Int): LeagueTable?
    fun deleteByLeagueId(leagueId: Int)
}

@Service
class TableService(private val tableRepository: TableRepository) {

    fun getTableById(id: Int) = tableRepository.findByLeagueId(id)

    fun updateTable(table: LeagueTable): LeagueTable {
        tableRepository.deleteByLeagueId(table.leagueId)
        return tableRepository.save(table)
    }

}