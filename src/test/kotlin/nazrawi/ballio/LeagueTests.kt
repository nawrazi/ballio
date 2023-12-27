package nazrawi.ballio

import nazrawi.ballio.entitiy.League
import nazrawi.ballio.exceptions.UnknownLeagueException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class LeagueTests {

    @Test
    fun testDisplayName() {
        assertEquals("Premier League", League.PremierLeague.displayName)
        assertEquals("La Liga", League.LaLiga.displayName)
        assertEquals("Serie A", League.SerieA.displayName)
        assertEquals("League 1", League.League1.displayName)
        assertEquals("Championship", League.Championship.displayName)
    }

    @Test
    fun testGetBySlug() {
        assertEquals(League.PremierLeague, League.getBySlug("premier-league"))
        assertEquals(League.LaLiga, League.getBySlug("la-liga"))
        assertEquals(League.SerieA, League.getBySlug("serie-a"))
        assertEquals(League.League1, League.getBySlug("league1"))
        assertEquals(League.Championship, League.getBySlug("championship"))

        assertThrows<UnknownLeagueException> {
            League.getBySlug("unknown-slug")
        }
    }
}