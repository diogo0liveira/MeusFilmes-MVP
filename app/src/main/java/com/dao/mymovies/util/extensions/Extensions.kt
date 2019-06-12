package com.dao.mymovies.util.extensions

import android.graphics.Bitmap
import android.graphics.Color
import android.widget.ImageView
import androidx.core.graphics.ColorUtils
import androidx.palette.graphics.Palette
import com.bumptech.glide.load.resource.bitmap.BitmapTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.dao.mymovies.R
import com.dao.mymovies.util.GlideApp

/**
 * Created in 27/03/19 16:25.
 *
 * @author Diogo Oliveira.
 */
fun ImageView.load(uri: String?, request: RequestListener<Bitmap>? = null)
{
    GlideApp.with(context)
            .asBitmap()
            .load(uri)
            .listener(request)
            .thumbnail(0.50F)
            .error(R.drawable.vd_movie_24dp)
            .transition(BitmapTransitionOptions.withCrossFade(150))
            .into(this)
}

fun Palette.contrastColor(): Int
{
    val most = swatches.maxBy { it.population }
    return if(ColorUtils.calculateLuminance(most?.rgb ?: Color.BLACK) > 0.5) Color.BLACK else Color.WHITE
}

