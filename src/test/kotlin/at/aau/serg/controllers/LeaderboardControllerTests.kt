package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.controller
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import org.mockito.Mockito.`when` as whenever // when is a reserved keyword in Kotlin

class LeaderboardControllerTests {

    private lateinit var mockedService: GameResultService
    private lateinit var controller: LeaderboardController

    @BeforeEach
    fun setup() {
        mockedService = mock<GameResultService>()
        controller = LeaderboardController(mockedService)
    }

    @Test
    fun test_getLeaderboard_correctScoreSorting() {
        val first = GameResult(1, "first", 20, 20.0)
        val second = GameResult(2, "second", 15, 10.0)
        val third = GameResult(3, "third", 10, 15.0)

        whenever(mockedService.getGameResults()).thenReturn(listOf(second, first, third))

        val res: List<GameResult> = controller.getLeaderboard(null)

        verify(mockedService).getGameResults()
        assertEquals(3, res.size)
        assertEquals(first, res[0])
        assertEquals(second, res[1])
        assertEquals(third, res[2])
    }

    @Test
    fun test_getLeaderboard_sameScore_CorrectIdSorting() {
        val first = GameResult(1, "first", 20, 20.0)
        val second = GameResult(2, "second", 20, 10.0)
        val third = GameResult(3, "third", 20, 15.0)

        whenever(mockedService.getGameResults()).thenReturn(listOf(second, first, third))

        val res: List<GameResult> = controller.getLeaderboard(null)

        verify(mockedService).getGameResults()
        assertEquals(3, res.size)
        assertEquals(first, res[2])
        assertEquals(second, res[0])
        assertEquals(third, res[1])
    }
    @Test fun test_getLeaderboardComplete() {
        val first = GameResult(1, "first", 20, 20.0)
        val second = GameResult(2, "second", 19, 10.0)
        val third = GameResult(3, "third", 18, 15.0)
        val fourth = GameResult(4, "fourth", 17, 20.0)
        val fifth = GameResult(5, "fifth", 16, 10.0)
        val sixth = GameResult(6, "sixth", 15, 15.0)
        val seventh = GameResult(7, "seventh", 14, 20.0)
        val eighth = GameResult(8, "eigth", 13, 10.0)
        val ninth = GameResult(9, "ninth", 12, 15.0)
        val tenth = GameResult(10, "tenth", 11, 20.0)

        whenever(mockedService.getGameResults()).thenReturn( listOf(first, second, third, fourth, fifth, sixth, seventh, eighth, ninth, tenth) )

        val res: List<GameResult> = controller.getLeaderboard(5)

        verify(mockedService).getGameResults()

        assertEquals(7, res.size)
        assertEquals(second, res[0])
        assertEquals(third, res[1])
        assertEquals(fourth, res[2])
        assertEquals(fifth, res[3])
        assertEquals(sixth, res[4])
        assertEquals(seventh, res[5])
        assertEquals(eighth, res[6])
    }
    @Test
    fun test_getLeaderboard_rankNegative() {

        val first = GameResult(1,"first",20,20.0)

        whenever(mockedService.getGameResults()).thenReturn(listOf(first))

        assertFailsWith<ResponseStatusException> {
            controller.getLeaderboard(-1)
        }
    }

    @Test
    fun test_getLeaderboard_rankTooLarge() {

        val first = GameResult(1,"first",20,20.0)

        whenever(mockedService.getGameResults()).thenReturn(listOf(first))

        assertFailsWith<ResponseStatusException> {
            controller.getLeaderboard(5)
        }
    }

}