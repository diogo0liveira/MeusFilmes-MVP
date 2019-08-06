package com.dao.mymovies.network

import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull

/**
 * Created in 06/08/19 14:58.
 *
 * @author Diogo Oliveira.
 */
object MediaType
{
    val NONE: MediaType? = "".toMediaTypeOrNull()
//    val JSON: MediaType = "application/json; charset=utf-8".toMediaType()
}