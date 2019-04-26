package com.dao.mymovies.features.detail

import android.os.Bundle
import androidx.annotation.StringRes
import com.dao.mymovies.base.mvp.IPresenter
import com.dao.mymovies.base.mvp.IView
import com.dao.mymovies.model.Movie
import com.dao.mymovies.util.annotation.Duration

/**
 * Created in 03/08/18 16:59.
 *
 * @author Diogo Oliveira.
 */
interface MovieDetailInteractor
{
    interface View : IView
    {
        fun putOnForm(movie: Movie)

        fun changeMovieFavoriteSuccess()

        fun showToast(@StringRes text: Int, @Duration duration: Int)
    }

    interface Presenter : IPresenter<View>
    {
        fun terminate()

        fun movieAction()

        fun onSaveInstanceState(outState: Bundle)

        fun onRestoreInstanceState(bundle: Bundle?, savedState: Boolean)
    }
}