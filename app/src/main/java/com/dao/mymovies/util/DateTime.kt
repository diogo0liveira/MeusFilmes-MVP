package com.dao.mymovies.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created in 17/04/19 13:43.
 *
 * @author Diogo Oliveira.
 */
private const val PATTERN_PARSER = "yyyy-MM-dd"

object DateTime
{
    private val parser = SimpleDateFormat(PATTERN_PARSER, Locale.ENGLISH)
    private val formatter = DateFormat.getDateInstance(DateFormat.LONG)

    @JvmStatic
    fun parse(date: String): Date
    {
        return parser.parse(date)
    }

    @JvmStatic
    fun dateFormatLong(date: Date): String
    {
        return formatter.format(date)
    }
}