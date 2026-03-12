package at.aau.serg.controllers

import at.aau.serg.models.GameResult
import at.aau.serg.services.GameResultService
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import kotlin.test.Test
import kotlin.test.assertEquals
import org.mockito.Mockito.`when` as whenever

class GameResultControllerTests {

    private lateinit var mockedService: GameResultService
    private lateinit var controller: GameResultController

    @BeforeEach
    fun setup() {
        mockedService = mock<GameResultService>()
        controller = GameResultController(mockedService)
    }

    @Test
    fun test_getGameResult_returnsObject() {
        val result = GameResult(1,"player1",20,15.0)

        whenever(mockedService.getGameResult(1)).thenReturn(result)

        val res = controller.getGameResult(1)

        verify(mockedService).getGameResult(1)
        assertEquals(result,res)
    }

    @Test
    fun test_getAllGameResults_returnsList() {
        val result1 = GameResult(1,"player1",20,15.0)
        val result2 = GameResult(2,"player2",18,16.0)

        whenever(mockedService.getGameResults()).thenReturn(listOf(result1,result2))

        val res = controller.getAllGameResults()

        verify(mockedService).getGameResults()
        assertEquals(2,res.size)
        assertEquals(result1,res[0])
        assertEquals(result2,res[1])
    }

    @Test
    fun test_addGameResult_callsService() {
        val result = GameResult(1,"player1",20,15.0)

        controller.addGameResult(result)

        verify(mockedService).addGameResult(result)
    }

    @Test
    fun test_deleteGameResult_callsService() {

        controller.deleteGameResult(1)

        verify(mockedService).deleteGameResult(1)
    }
}