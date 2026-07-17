package com.s8116504.assignment2

import com.s8116504.assignment2.data.model.LoginResponse
import com.s8116504.assignment2.data.repository.ApiRepository
import com.s8116504.assignment2.ui.login.LoginState
import com.s8116504.assignment2.ui.login.LoginViewModel
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
class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel
    private lateinit var repository: ApiRepository
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        repository = mockk()
        Dispatchers.setMain(testDispatcher)
        viewModel = LoginViewModel(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `login success updates state to Success with keypass`() = runTest(testDispatcher) {
        // Given
        coEvery { repository.login("Amy", "s8116504") } returns LoginResponse("photography")

        // When
        viewModel.login("Amy", "s8116504")
        advanceUntilIdle()

        // Then
        val state = viewModel.loginState.value
        assertTrue(state is LoginState.Success)
        assertEquals("photography", (state as LoginState.Success).keypass)
    }

    @Test
    fun `login failure updates state to Error`() = runTest(testDispatcher) {
        // Given
        coEvery { repository.login(any(), any()) } throws Exception("Invalid credentials")

        // When
        viewModel.login("wrong", "credentials")
        advanceUntilIdle()

        // Then
        val state = viewModel.loginState.value
        assertTrue(state is LoginState.Error)
        assertEquals("Invalid credentials", (state as LoginState.Error).message)
    }

    @Test
    fun `initial state is Idle`() {
        // Then
        assertTrue(viewModel.loginState.value is LoginState.Idle)
    }
}