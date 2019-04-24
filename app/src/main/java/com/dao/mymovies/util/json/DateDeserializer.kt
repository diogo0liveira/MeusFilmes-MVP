package com.dao.mymovies.util.json

import com.dao.mymovies.util.DateTime
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type
import java.util.*

/**
 * Created in 24/04/19 17:15.
 *
 * @author Diogo Oliveira.
 */
class DateDeserializer : JsonDeserializer<Date>
{
    override fun deserialize(json: JsonElement, type: Type, context: JsonDeserializationContext?): Date?
    {
        return if(json.asString.isNullOrEmpty()) null else DateTime.parse(json.asString)
    }
}