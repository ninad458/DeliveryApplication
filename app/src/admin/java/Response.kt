package com.enigma.myapplication

import com.squareup.moshi.Json

data class Response(val orders: List<Task> = listOf())

data class Task(val id: String, val status: Status)

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