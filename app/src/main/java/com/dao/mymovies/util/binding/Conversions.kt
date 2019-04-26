package com.dao.mymovies.util.binding

import android.view.View
import androidx.databinding.BindingConversion


/**
 * Created in 26/04/19 15:12.
 *
 * @author Diogo Oliveira.
 */
@BindingConversion
fun convertVisibility(visible: Boolean): Int
{
    return if(visible) View.VISIBLE else View.GONE
}