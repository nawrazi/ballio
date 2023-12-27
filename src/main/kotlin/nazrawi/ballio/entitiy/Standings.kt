package nazrawi.ballio.entitiy

import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
class Standings(
    @Id var leagueId: Int,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "leagueId")
    var teams: List<Team>,
    private var updatedAt: String = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
) {
    val isOutdated: Boolean
        get() = LocalDateTime
                .parse(updatedAt, DateTimeFormatter.ISO_DATE_TIME)
                .isBefore(LocalDateTime.now().minusHours(REFRESH_FREQUENCY_IN_HOURS.toLong()))

    fun toDto() = StandingsDto(
        leagueId = leagueId,
        teams = teams.map { it.toDto() },
        updatedAt = updatedAt,
    )

    companion object {
        const val REFRESH_FREQUENCY_IN_HOURS = 1
    }
}

data class StandingsDto(
    val leagueId: Int,
    val teams: List<TeamDto>,
    val updatedAt: String,
)