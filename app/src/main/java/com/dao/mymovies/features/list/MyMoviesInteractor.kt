package com.dao.mymovies.features.list

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dao.mymovies.base.OnCollectionChangedListener
import com.dao.mymovies.base.mvp.IPresenter
import com.dao.mymovies.base.mvp.IView
import com.dao.mymovies.features.adapter.MyMoviesAdapter
import com.dao.mymovies.model.Movie
import com.dao.mymovies.model.Order

/**
 * Created in 03/08/18 12:02.
 *
 * @author Diogo Oliveira.
 */
interface MyMoviesInteractor
{
    interface View : IView, OnCollectionChangedListener, MyMoviesAdapter.MovieViewOnClickListener
    {
        fun startSearchMoviesActivity()
    }

    interface Presenter : IPresenter<View>
    {
        fun moviesObserver(): LiveData<PagedList<Movie>>

        fun moviesOrderBy(order: Order)
    }
}