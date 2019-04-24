package com.dao.mymovies.util.extensions

import android.widget.ImageView
import com.dao.mymovies.R
import com.dao.mymovies.util.GlideApp

/**
 * Created in 27/03/19 16:25.
 *
 * @author Diogo Oliveira.
 */
fun ImageView.load(uri: String?)
{
    GlideApp.with(context)
            .load(uri)
            .placeholder(R.drawable.vd_movie_24dp)
            .error(R.drawable.vd_movie_24dp)
            .into(this)
}