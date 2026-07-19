package com.s8116504.assignment2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.s8116504.assignment2.data.model.CardDetails
import com.s8116504.assignment2.ui.dashboard.DashboardState
import com.s8116504.assignment2.ui.dashboard.DashboardViewModel
import com.s8116504.assignment2.ui.dashboard.EntityAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.core.view.GravityCompat
import androidx.navigation.NavOptions

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
        android.util.Log.d("DASH_DEBUG", "Keypass received: '$keypass'")

        recyclerView.layoutManager = GridLayoutManager(requireContext(), 2)

        val titles = listOf(
            "bout' JWW",
            "bout' CBG",
            "040319.txt",
            "260515.svt",
            "film_jww",
            "film_cbg"
        )

        val cardDetails = listOf(
            CardDetails(
                title = "bout' JWW",
                subtitle = "Everything about Jeon Wonwoo",
                description = "Known for his sharp performance and thoughtful presence, Wonwoo serves as a pillar of depth in SEVENTEEN. He balances a calm, intellectual demeanor with a profound passion " +
                        "for visual storytelling, capturing the world around him with a unique, introspective lens.",
                posts = "177", rating = "3.0", since = "1996",
                image = R.drawable.img_1,
                gallery = listOf(R.drawable.g11, R.drawable.g12, R.drawable.g13, R.drawable.g14, R.drawable.g15, R.drawable.g16)
            ),
            CardDetails(
                title = "bout' CBG",
                subtitle = "Every moment about Choi Beomgyu",
                description = "As a vibrant energy source for Tomorrow X Together, Beomgyu brings a beautiful blend of bright charisma and deep sensitivity. His expressive nature allows him to " +
                        "connect effortlessly with fans, embodying the multifaceted emotions of modern youth.",
                posts = "133", rating = "2.5", since = "2001",
                image = R.drawable.img_2,
                gallery = listOf(R.drawable.g21, R.drawable.g22, R.drawable.g23, R.drawable.g24, R.drawable.g25, R.drawable.g26)
            ),
            CardDetails(
                title = "040319.txt",
                subtitle = "TXT documentary",
                description = "TXT Documentary photography from March 4, 2019. As the voices of Gen Z, Tomorrow X Together crafts magical, narrative-driven concepts that explore the bittersweet journey of growing up. Their visual identity is " +
                        "a dreamlike exploration of youth, filled with fantasy, vulnerability, and artistic growth.",
                posts = "403", rating = "5.0", since = "2019",
                image = R.drawable.img_3,
                gallery = listOf(
                    R.drawable.g31,
                    R.drawable.g32,
                    R.drawable.g33,
                    R.drawable.g34,
                    R.drawable.g35,
                    R.drawable.g36
                )
            ),
            CardDetails(
                title = "260515.svt",
                subtitle = "Seventeen zip file",
                description = "A powerhouse of synchronization and self-produced artistry, SEVENTEEN redefines the strength of unity. Their music and imagery celebrate the chaotic joy of brotherhood" +
                        ", the power of teamwork, and an enduring bond that transforms everyday moments into shared triumphs.",
                posts = "265", rating = "13.0", since = "2015",
                image = R.drawable.img_4,
                gallery = listOf(R.drawable.g41, R.drawable.g42, R.drawable.g43, R.drawable.g44, R.drawable.g45, R.drawable.g46)
            ),
            CardDetails(
                title = "film_jww",
                subtitle = "Film Roll",
                description = "This collection highlights an introspective journey through film, defined by cinematic framing and nostalgic monochrome tones. Each shot captures quiet landscapes and " +
                        "raw, candid moments, turning passing glances into permanent, deeply evocative stories.",
                posts = "6", rating = "5.0", since = "2024",
                image = R.drawable.img_5,
                gallery = listOf(R.drawable.g51, R.drawable.g52, R.drawable.g53, R.drawable.g54, R.drawable.g55, R.drawable.g56)
            ),
            CardDetails(
                title = "film_cbg",
                subtitle = "Film Roll",
                description = "Filled with warm tones and cozy, intimate lighting, this album offers a comforting glimpse into backstage memories and daily life. These personal snapshots preserve the raw essence " +
                        "of youth, inviting viewers into a world of comfort and shared nostalgia.",
                posts = "12", rating = "5.0", since = "2023",
                image = R.drawable.img_6,
                gallery = listOf(R.drawable.g61, R.drawable.g62, R.drawable.g63, R.drawable.g64, R.drawable.g65, R.drawable.g66)
            )
        )

        adapter = EntityAdapter(emptyList()) { entity, position ->
            val card = cardDetails[position % cardDetails.size]
            val bundle = Bundle()
            bundle.putString("title", card.title)
            bundle.putString("subtitle", card.subtitle)
            bundle.putString("description", card.description)
            bundle.putString("posts", card.posts)
            bundle.putString("rating", card.rating)
            bundle.putString("since", card.since)
            bundle.putInt("image", card.image)
            bundle.putIntArray("gallery", card.gallery.toIntArray())
            findNavController().navigate(
                R.id.action_dashboardFragment_to_detailsFragment,
                bundle
            )
        }
        recyclerView.adapter = adapter
        val drawerLayout = view.findViewById<DrawerLayout>(R.id.drawerLayout)
        val navigationView = view.findViewById<NavigationView>(R.id.navigationView)
        val ivMenu = view.findViewById<ImageView>(R.id.ivMenu)

// Open drawer on menu icon click
        ivMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

// Handle drawer item clicks
        navigationView.setNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> {
                    drawerLayout.closeDrawers()
                }
                R.id.menu_logout -> {
                    drawerLayout.closeDrawers()

                    // Build options to clear the entire history
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo(R.id.nav_graph, true) // Clears everything including root
                        .build()

                    findNavController().navigate(R.id.loginFragment, null, navOptions)
                }
                R.id.menu_about -> {
                    drawerLayout.closeDrawers()
                    android.widget.Toast.makeText(
                        requireContext(),
                        "Aperio — Photography Studio App v1.0",
                        android.widget.Toast.LENGTH_SHORT
                    ).show()
                }
            }
            true
        }

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