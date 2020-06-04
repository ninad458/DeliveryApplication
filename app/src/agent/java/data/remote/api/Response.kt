package com.enigma.myapplication.data.remote.api

import com.squareup.moshi.Json

data class GetTasksResponse(val orders: List<Task> = listOf())

data class Task(
    val id: String,
    val status: Status,
    val item: String,
    val details: String,
    val address: String,
    val name: String
)

enum class Status {
    @Json(name = "queued")
    QUEUED,

    @Json(name = "in-transit")
    IN_TRANSIT,

    @Json(name = "delivered")
    DELIVERED,

    @Json(name = "cancelled")
    CANCELLED
}

data class UpdateTasksResponse(val message: String)