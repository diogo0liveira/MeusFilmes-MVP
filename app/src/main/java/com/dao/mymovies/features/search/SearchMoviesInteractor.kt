package com.dao.mymovies.features.search

import android.content.Context
import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dao.mymovies.base.mvp.IPresenter
import com.dao.mymovies.base.mvp.IView
import com.dao.mymovies.model.Movie
import com.dao.mymovies.util.annotation.Duration

/**
 * Created in 06/08/18 10:42.
 *
 * @author Diogo Oliveira.
 */
interface SearchMoviesInteractor
{
    interface View : IView
    {
        fun context(): Context

        fun changeSearchProgress(visible: Boolean)

        fun changePaginationProgress(visible: Boolean)

        fun showToast(@StringRes text: Int, @Duration duration: Int)
    }

    interface Presenter : IPresenter<View>
    {
        fun searchObserver(): LiveData<PagedList<Movie>>

        fun searchMovies(query: String)

        fun terminate()
    }
}