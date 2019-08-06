package com.dao.mymovies.features.search

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dao.mymovies.base.OnCollectionChangedListener
import com.dao.mymovies.base.mvp.IPresenter
import com.dao.mymovies.base.mvp.IView
import com.dao.mymovies.features.adapter.MyMoviesAdapter
import com.dao.mymovies.model.Movie
import com.dao.mymovies.network.NetworkState
import com.dao.mymovies.util.annotation.Duration

/**
 * Created in 06/08/18 10:42.
 *
 * @author Diogo Oliveira.
 */
interface SearchMoviesInteractor
{
    interface View : IView, OnCollectionChangedListener, MyMoviesAdapter.MovieViewOnClickListener
    {
        fun networkStateObserver(observable: LiveData<NetworkState>)

        fun showToast(@StringRes text: Int, @Duration duration: Int)

        fun notifyError(@StringRes text: Int, block: () -> Unit)

        fun executeRequireNetwork(block: () -> Unit)
    }

    interface Presenter : IPresenter<View>
    {
        fun searchObserver(): LiveData<PagedList<Movie>>

        fun searchMovies(query: String)

        fun terminate()
    }
}