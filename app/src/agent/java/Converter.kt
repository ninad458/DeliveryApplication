package com.enigma.myapplication

import androidx.room.TypeConverter

class Converter {
    @TypeConverter
    fun toStatus(value: Int) = enumValues<Status>()[value]

    @TypeConverter
    fun fromStatus(value: Status) = value.ordinal
}