package com.s8116504.assignment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.s8116504.assignment2.ui.dashboard.DashboardState
import com.s8116504.assignment2.ui.dashboard.DashboardViewModel
import com.s8116504.assignment2.ui.dashboard.EntityAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DashboardFragment : Fragment() {

    private val viewModel: DashboardViewModel by viewModels()
    private lateinit var adapter: EntityAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.rvDashboard)
        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottomNav)

        val keypass = arguments?.getString("keypass") ?: ""

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        adapter = EntityAdapter(emptyList()) { entity ->
            val bundle = Bundle()
            bundle.putString("title", entity.title)
            bundle.putString("description", entity.description)
            findNavController().navigate(
                R.id.action_dashboardFragment_to_detailsFragment,
                bundle
            )
        }
        recyclerView.adapter = adapter

        bottomNav.selectedItemId = R.id.nav_home
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_settings -> true
                else -> true
            }
        }

        viewModel.fetchDashboard(keypass)

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dashboardState.collect { state ->
                    when (state) {
                        is DashboardState.Idle -> {}
                        is DashboardState.Loading -> {}
                        is DashboardState.Success -> {
                            adapter.updateData(state.entities.take(6))
                        }
                        is DashboardState.Error -> {
                            android.widget.Toast.makeText(
                                requireContext(),
                                state.message,
                                android.widget.Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }
        }
    }
}