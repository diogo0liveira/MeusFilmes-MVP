package com.dao.mymovies.pojo

import com.dao.mymovies.model.Movie
import com.squareup.moshi.Json

/**
 * Created in 06/08/18 09:15.
 *
 * @author Diogo Oliveira.
 */
data class SearchResult(
        @Json(name = "page")
        val page: Int,
        @Json(name = "total_results")
        val totalResults: Int,
        @Json(name = "total_pages")
        val totalPages: Int,
        @Json(name = "results")
        val results: List<Movie>)