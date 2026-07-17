package com.s8116504.assignment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tvTitle = view.findViewById<TextView>(R.id.tvDetailTitle)
        val tvDescription = view.findViewById<TextView>(R.id.tvDetailDescription)
        val ivBack = view.findViewById<ImageView>(R.id.ivBack)
        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottomNav)

        // Get data from DashboardFragment
        val title = arguments?.getString("title") ?: "Unknown"
        val description = arguments?.getString("description") ?: ""

        tvTitle.text = title
        tvDescription.text = description.ifEmpty { "No description available." }

        // Back button
        ivBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // Bottom nav
        bottomNav.selectedItemId = R.id.nav_settings
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    findNavController().popBackStack()
                    true
                }
                R.id.nav_settings -> true
                else -> true
            }
        }
    }
}