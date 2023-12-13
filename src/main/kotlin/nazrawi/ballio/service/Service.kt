package nazrawi.ballio.service

import nazrawi.ballio.entitiy.Table
import nazrawi.ballio.repository.TableRepository
import org.springframework.stereotype.Service

@Service
class TableService(private val tableRepository: TableRepository) {

    fun getTableById(id: Int) = tableRepository.findByLeagueId(id)

    fun updateTable(table: Table): Table {
        tableRepository.deleteByLeagueId(table.leagueId)
        return tableRepository.save(table)
    }

}
