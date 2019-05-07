package com.dao.mymovies.features.list

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dao.mymovies.data.MovieRepository
import com.dao.mymovies.di.annotations.ActivityScoped
import com.dao.mymovies.model.Movie
import com.dao.mymovies.model.Order
import javax.inject.Inject

/**
 * Created in 03/08/18 12:03.
 *
 * @author Diogo Oliveira.
 */
@ActivityScoped
class MyMoviesPresenter @Inject constructor(repository: MovieRepository) : MyMoviesInteractor.Presenter
{
    private lateinit var view: MyMoviesInteractor.View
    private val movies: LiveData<PagedList<Movie>>
    private var order: Order = Order.TITLE

    init
    {
        val config = PagedList.Config.Builder().setPageSize(30).build()
        movies = LivePagedListBuilder(repository.getMovies().mapByPage { orderBy(it) }, config).build()
    }

    override fun initialize(view: MyMoviesInteractor.View)
    {
        this.view = view
        this.view.initializeView()
    }

    override fun moviesObserver(): LiveData<PagedList<Movie>> = movies

    private fun orderBy(movies: List<Movie>): List<Movie>
    {
        return when(order)
        {
            Order.TITLE -> movies.sortedBy { movie -> movie.title }
            Order.DATE -> movies.sortedBy { movie -> movie.releaseDate }
        }
    }

    override fun moviesOrderBy(order: Order)
    {
        this.order = order
        movies.value?.dataSource?.invalidate()
    }
}