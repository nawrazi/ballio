package nazrawi.ballio

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
@RequestMapping("/table")
class TableController(private val tableService: TableService) {

	@GetMapping("/{id}")
	fun getAllTeams(@PathVariable id: Int): LeagueTableDto {

		var table = tableService.getTableById(id)

		if (table == null || table.isAtLeastOneDayOld)
			table = tableService.updateTable(WebScraper(League.getById(id)).scrape())

		return table.toDto()
	}

}
