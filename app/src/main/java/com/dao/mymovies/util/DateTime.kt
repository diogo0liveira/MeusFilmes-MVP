package com.dao.mymovies.util

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created in 17/04/19 13:43.
 *
 * @author Diogo Oliveira.
 */
private const val PATTERN_PARSER = "yyyy-MM-dd"
private const val DATE_MEDIUM = "dd/MM/yyyy"

object DateTime
{
    private val formatter = SimpleDateFormat(PATTERN_PARSER, Locale.ENGLISH)

    @JvmStatic
    fun parse(date: String): Date
    {
        formatter.applyPattern(PATTERN_PARSER)
        return formatter.parse(date)
    }

    @JvmStatic
    fun dateFormatMedium(date: Date): String
    {
        formatter.applyPattern(DATE_MEDIUM)
        return formatter.format(date)
    }
}