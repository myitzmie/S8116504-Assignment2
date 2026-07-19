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
        val tvSubtitle = view.findViewById<TextView>(R.id.tvEntityType)
        val tvDescription = view.findViewById<TextView>(R.id.tvDetailDescription)
        val tvPosts = view.findViewById<TextView>(R.id.tvStat1Value)
        val tvRating = view.findViewById<TextView>(R.id.tvStat2Value)
        val tvSince = view.findViewById<TextView>(R.id.tvStat3Value)
        val ivIcon = view.findViewById<ImageView>(R.id.ivEntityIcon)
        val ivBack = view.findViewById<ImageView>(R.id.ivBack)
        val bottomNav = view.findViewById<BottomNavigationView>(R.id.bottomNav)

        // Get all data from bundle
        tvTitle.text = arguments?.getString("title") ?: ""
        tvSubtitle.text = arguments?.getString("subtitle") ?: ""
        tvDescription.text = arguments?.getString("description") ?: ""
        tvPosts.text = arguments?.getString("posts") ?: "0"
        tvRating.text = arguments?.getString("rating") ?: "0"
        tvSince.text = arguments?.getString("since") ?: ""

        // Hero image
        val imageRes = arguments?.getInt("image") ?: R.drawable.background
        ivIcon.setImageResource(imageRes)

        // Gallery images
        val galleryIds = arguments?.getIntArray("gallery")
        val galleryViews = listOf(
            view.findViewById<ImageView>(R.id.ivGallery1),
            view.findViewById<ImageView>(R.id.ivGallery2),
            view.findViewById<ImageView>(R.id.ivGallery3),
            view.findViewById<ImageView>(R.id.ivGallery4),
            view.findViewById<ImageView>(R.id.ivGallery5),
            view.findViewById<ImageView>(R.id.ivGallery6)
        )
        galleryIds?.forEachIndexed { index, resId ->
            if (index < galleryViews.size) {
                galleryViews[index].setImageResource(resId)
            }
        }

        ivBack.setOnClickListener { findNavController().popBackStack() }

        bottomNav.selectedItemId = R.id.nav_settings
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> { findNavController().popBackStack(); true }
                R.id.nav_settings -> true
                else -> true
            }
        }
    }
}