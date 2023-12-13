package nazrawi.ballio.entitiy

import jakarta.persistence.*

@Entity
class Team(
    var name: String,
    var rank: Int,
    var points: Int,
    var gd: Int,
    var leagueId: Int,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
) {
    fun toDto() = TeamDto(
        name = name,
        rank = rank,
        points = points,
        gd = gd,
        leagueId = leagueId,
        id = id,
    )
}

data class TeamDto(
    val name: String,
    val rank: Int,
    val points: Int,
    val gd: Int,
    val leagueId: Int,
    val id: Long,
)
