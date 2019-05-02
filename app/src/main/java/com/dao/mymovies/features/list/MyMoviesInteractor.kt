package com.dao.mymovies.features.list

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dao.mymovies.base.mvp.IPresenter
import com.dao.mymovies.base.mvp.IView
import com.dao.mymovies.model.Movie

/**
 * Created in 03/08/18 12:02.
 *
 * @author Diogo Oliveira.
 */
interface MyMoviesInteractor
{
    interface View : IView
    {
        fun startSearchMoviesActivity()
    }

    interface Presenter : IPresenter<View>
    {
        fun moviesObserver(): LiveData<PagedList<Movie>>
    }
}