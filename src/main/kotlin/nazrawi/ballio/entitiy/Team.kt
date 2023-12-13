package nazrawi.ballio.entitiy

import jakarta.persistence.*

@Entity
class Team(
    var name: String,
    var ranking: Int,
    var points: Int,
    var gd: Int,
    var leagueId: Int,
    var crest: String,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0L,
) {
    fun toDto() = TeamDto(
        name = name,
        ranking = ranking,
        points = points,
        gd = gd,
        leagueId = leagueId,
        crest = crest,
        id = id,
    )
}

data class TeamDto(
    val name: String,
    val ranking: Int,
    val points: Int,
    val gd: Int,
    val leagueId: Int,
    val crest: String,
    val id: Long,
)
