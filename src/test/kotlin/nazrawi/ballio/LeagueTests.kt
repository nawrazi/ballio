package nazrawi.ballio

import nazrawi.ballio.entitiy.League
import nazrawi.ballio.exceptions.UnknownLeagueException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class LeagueTests {

    @ParameterizedTest(name = "Display name of {0} should be {1}")
    @MethodSource("getLeagueDisplayNames")
    fun testDisplayName(league: League, expectedDisplayName: String) {
        assertEquals(league.displayName, expectedDisplayName)
    }

    @ParameterizedTest(name = "League from slug {0} should be {1}")
    @MethodSource("getLeagueSlugs")
    fun testGetBySlug(slug: String, expectedLeague: League) {
        assertEquals(League.getBySlug(slug), expectedLeague)

        assertThrows<UnknownLeagueException> {
            League.getBySlug("$slug-x")
        }
    }

    companion object {
        @JvmStatic
        fun getLeagueDisplayNames(): Stream<Arguments> {
            return Stream.of(
                Arguments.of(League.PremierLeague, "Premier League"),
                Arguments.of(League.LaLiga, "La Liga"),
                Arguments.of(League.SerieA, "Serie A"),
                Arguments.of(League.League1, "League 1"),
                Arguments.of(League.Championship, "Championship"),
            )
        }

        @JvmStatic
        fun getLeagueSlugs(): Stream<Arguments> {
            return Stream.of(
                Arguments.of("premier-league", League.PremierLeague),
                Arguments.of("la-liga", League.LaLiga),
                Arguments.of("serie-a", League.SerieA),
                Arguments.of("league1", League.League1),
                Arguments.of("championship", League.Championship),
            )
        }
    }
}