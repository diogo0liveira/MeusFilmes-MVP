package com.dao.mymovies.util.binding

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
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
@BindingAdapter("visible")
fun visible(view: View, visible: Boolean)
{
    view.visibility = if(visible) View.VISIBLE else View.GONE
}

@BindingAdapter(value = ["text", "date"], requireAll = false)
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

@BindingAdapter("cover")
fun cover(view: ImageView, uri: String?)
{
    uri?.let { view.load(TheMovieApi.COVER + uri) } ?: run {
        view.setImageResource(R.drawable.vd_movie_120dp)
    }
}

@BindingAdapter("favorite")
fun favorite(view: FloatingActionButton, favorite: Boolean)
{
    view.setImageResource(if(favorite) R.drawable.vd_favorite_24dp else R.drawable.vd_not_favorite_24dp)
}

@BindingAdapter("asyncText", "android:textSize", requireAll = false)
fun asyncText(view: TextView, text: CharSequence, textSize: Int?)
{
    textSize?.let { view.textSize = it.toFloat() }
    val params = TextViewCompat.getTextMetricsParams(view)
    (view as AppCompatTextView).setTextFuture(PrecomputedTextCompat.getTextFuture(text, params, null))
}