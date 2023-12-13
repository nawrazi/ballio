package nazrawi.ballio.entitiy

import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Entity
class Standings(
    var leagueId: Int,
    @OneToMany(cascade = [CascadeType.ALL], mappedBy = "leagueId")
    var teams: List<Team>,
    var updatedAt: String = LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
) {
    val isOutdated: Boolean
        get() = LocalDateTime
                .parse(updatedAt, DateTimeFormatter.ISO_DATE_TIME)
                .isBefore(LocalDateTime.now().minusDays(1))

    fun toDto() = StandingsDto(
        leagueId = leagueId,
        teams = teams.map { it.toDto() },
        updatedAt = updatedAt,
        id = id,
    )
}

data class StandingsDto(
    val leagueId: Int,
    val teams: List<TeamDto>,
    val updatedAt: String,
    val id: Long,
)