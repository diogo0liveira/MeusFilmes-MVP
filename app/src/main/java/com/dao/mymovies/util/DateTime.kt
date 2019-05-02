package com.dao.mymovies.util

import java.text.DateFormat
import java.util.*

/**
 * Created in 17/04/19 13:43.
 *
 * @author Diogo Oliveira.
 */
object DateTime
{
    private val formatter = DateFormat.getDateInstance(DateFormat.LONG)

    @JvmStatic
    fun dateFormatLong(date: Date): String
    {
        return formatter.format(date)
    }
}