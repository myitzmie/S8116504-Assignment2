package com.s8116504.assignment2.data.model

data class CardDetails(
    val title: String,
    val subtitle: String,
    val description: String,
    val posts: String,
    val rating: String,
    val since: String,
    val image: Int,
    val gallery: List<Int>
)