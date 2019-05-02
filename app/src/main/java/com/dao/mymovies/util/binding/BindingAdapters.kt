package com.dao.mymovies.util.binding

import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.request.RequestListener
import com.dao.mymovies.R
import com.dao.mymovies.TheMovieApi
import com.dao.mymovies.util.DateTime
import com.dao.mymovies.util.extensions.load
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.*

/**
 * Created in 03/08/18 16:17.
 *
 * @author Diogo Oliveira.
 */
@BindingAdapter("text", "date", requireAll = false)
fun date(view: TextView, text: String?, date: Date?)
{
    var message = date.toString()
    date?.let { message = DateTime.dateFormatLong(it) }

    if(!text.isNullOrEmpty())
    {
        message = String.format(text, message)
    }

    view.text = message
}

@BindingAdapter("cover", "requestListener", requireAll = false)
fun cover(view: ImageView, uri: String?, request: RequestListener<Bitmap>?)
{
    view.load(TheMovieApi.COVER.plus(uri), request)
}

@BindingAdapter("favorite")
fun favorite(view: FloatingActionButton, favorite: Boolean)
{
    view.setImageResource(if(favorite) R.drawable.vd_favorite_24dp else R.drawable.vd_not_favorite_24dp)
}

@BindingAdapter("voteAverage")
fun voteAverage(view: RatingBar, average: Float)
{
    view.rating = (average / 2)
}

@BindingAdapter("asyncText", "android:textSize", requireAll = false)
fun asyncText(view: TextView, text: CharSequence, textSize: Int?)
{
    textSize?.let { view.textSize = it.toFloat() }
    val params = TextViewCompat.getTextMetricsParams(view)
    (view as AppCompatTextView).setTextFuture(PrecomputedTextCompat.getTextFuture(text, params, null))
}