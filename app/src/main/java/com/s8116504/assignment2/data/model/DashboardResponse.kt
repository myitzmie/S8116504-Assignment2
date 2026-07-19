package com.s8116504.assignment2.data.model

data class DashboardResponse(
    val entities: List<EntityResponse>,
    val entityTotal: Int
)
data class EntityResponse(
    val property1: String? = null,
    val property2: String? = null,
    val description: String? = null
)