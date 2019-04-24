package com.dao.mymovies.data.local.database.converts

import androidx.room.TypeConverter
import java.util.*


/**
 * Created in 24/04/19 12:15.
 *
 * @author Diogo Oliveira.
 */
class DateTypeConverter
{
    @TypeConverter
    fun fromTimestamp(value: Long?): Date?
    {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun toTimestamp(value: Date?): Long?
    {
        return value?.time
    }
}