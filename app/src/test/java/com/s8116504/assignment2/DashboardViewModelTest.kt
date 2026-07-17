package com.s8116504.assignment2

import com.s8116504.assignment2.data.model.Entity
import com.s8116504.assignment2.data.repository.ApiRepository
import com.s8116504.assignment2.ui.dashboard.DashboardState
import com.s8116504.assignment2.ui.dashboard.DashboardViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardViewModelTest {

    private lateinit var viewModel: DashboardViewModel
    private lateinit var repository: ApiRepository
    private val testDispatcher = StandardTestDispatcher()

    private val mockEntities = listOf(
        Entity(title = "Cinematic", description = "A cinematic style"),
        Entity(title = "Lo-fi Grain", description = "Vintage grain effect"),
        Entity(title = "Aesthetic", description = "Clean aesthetic look")
    )

    @Before
    fun setUp() {
        repository = mockk()
        Dispatchers.setMain(testDispatcher)
        viewModel = DashboardViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchDashboard success updates state to Success with entities`() = runTest(testDispatcher) {
        // Given
        coEvery { repository.getDashboard("photography") } returns mockEntities

        // When
        viewModel.fetchDashboard("photography")
        advanceUntilIdle()

        // Then
        val state = viewModel.dashboardState.value
        assertTrue(state is DashboardState.Success)
        assertEquals(3, (state as DashboardState.Success).entities.size)
        assertEquals("Cinematic", state.entities[0].title)
    }

    @Test
    fun `fetchDashboard failure updates state to Error`() = runTest(testDispatcher) {
        // Given
        coEvery { repository.getDashboard(any()) } throws Exception("Network error")

        // When
        viewModel.fetchDashboard("invalid")
        advanceUntilIdle()

        // Then
        val state = viewModel.dashboardState.value
        assertTrue(state is DashboardState.Error)
        assertEquals("Network error", (state as DashboardState.Error).message)
    }

    @Test
    fun `initial state is Idle`() {
        // Then
        assertTrue(viewModel.dashboardState.value is DashboardState.Idle)
    }

    @Test
    fun `fetchDashboard returns correct entity count`() = runTest(testDispatcher) {
        // Given
        coEvery { repository.getDashboard(any()) } returns mockEntities

        // When
        viewModel.fetchDashboard("photography")
        advanceUntilIdle()

        // Then
        val state = viewModel.dashboardState.value as DashboardState.Success
        assertEquals(3, state.entities.size)
    }
}