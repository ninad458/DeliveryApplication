package com.enigma.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "tasks")
data class TaskEntity(
    @PrimaryKey val id: String,
    @TypeConverters(Converter::class)
    @ColumnInfo(name = "status") val status: Status,
    @ColumnInfo(name = "item") val item: String,
    @ColumnInfo(name = "details") val details: String,
    val address: String,
    val name: String,
    val synced: Boolean = true
)