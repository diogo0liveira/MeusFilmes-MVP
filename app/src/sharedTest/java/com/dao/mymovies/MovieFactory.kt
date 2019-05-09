package com.dao.mymovies

import com.dao.mymovies.model.Movie
import java.util.*

/**
 * Created in 08/05/19 15:47.
 *
 * @author Diogo Oliveira.
 */
object MovieFactory
{
    fun build(
            id: Int,
            title: String = "Title",
            releaseDate: Date? = null,
            voteAverage: Float = 5.0F,
            overView: String = "OverView",
            cover: String? = null): Movie
    {
        return Movie(id, title, releaseDate, voteAverage, overView, cover)
    }
}

const val TITLE_A = "A Title"
const val TITLE_B = "B Title"