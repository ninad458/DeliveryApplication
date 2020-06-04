package com.enigma.myapplication.data.utils

import androidx.room.TypeConverter
import com.enigma.myapplication.data.remote.api.Status

class Converter {
    @TypeConverter
    fun toStatus(value: Int) = enumValues<Status>()[value]

    @TypeConverter
    fun fromStatus(value: Status) = value.ordinal
}