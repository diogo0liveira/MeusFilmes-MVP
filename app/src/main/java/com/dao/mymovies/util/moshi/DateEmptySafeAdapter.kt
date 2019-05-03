@file:Suppress("unused")

package com.dao.mymovies.util.moshi

import androidx.annotation.Nullable
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import java.io.IOException
import java.util.*

/**
 * Created in 03/05/19 09:15.
 *
 * @author Diogo Oliveira.
 */
object EmptyDateSafeAdapter
{
    @FromJson
    @Nullable
    @Throws(IOException::class)
    fun fromJson(jsonReader: JsonReader, delegate: JsonAdapter<Date>): Date?
    {
        val value = jsonReader.nextString()
       return if(value.isEmpty()) null else delegate.fromJsonValue(value)
    }
}