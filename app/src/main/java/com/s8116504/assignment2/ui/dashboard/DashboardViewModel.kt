package com.s8116504.assignment2.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.s8116504.assignment2.data.model.Entity
import com.s8116504.assignment2.data.repository.ApiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val repository: ApiRepository
) : ViewModel() {

    private val _dashboardState = MutableStateFlow<DashboardState>(DashboardState.Idle)
    val dashboardState: StateFlow<DashboardState> = _dashboardState

    fun fetchDashboard(keypass: String) {
        viewModelScope.launch {
            _dashboardState.value = DashboardState.Loading
            try {
                val entities = repository.getDashboard(keypass)
                _dashboardState.value = DashboardState.Success(entities)
            } catch (e: Exception) {
                _dashboardState.value = DashboardState.Error(e.message ?: "Failed to load")
            }
        }
    }
}

sealed class DashboardState {
    object Idle : DashboardState()
    object Loading : DashboardState()
    data class Success(val entities: List<Entity>) : DashboardState()
    data class Error(val message: String) : DashboardState()
}