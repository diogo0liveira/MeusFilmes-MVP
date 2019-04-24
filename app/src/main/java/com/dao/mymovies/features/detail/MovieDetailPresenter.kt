package com.dao.mymovies.features.detail

import android.os.Bundle
import android.widget.Toast
import com.dao.mymovies.Extras.MOVIE
import com.dao.mymovies.R
import com.dao.mymovies.data.repository.MoviesRepository
import com.dao.mymovies.model.Movie
import io.reactivex.disposables.CompositeDisposable

/**
 * Created in 03/08/18 16:59.
 *
 * @author Diogo Oliveira.
 */
class MovieDetailPresenter (private val repository: MoviesRepository) : MovieDetailInteractor.Presenter
{
    private lateinit var view: MovieDetailInteractor.View
    private val composite: CompositeDisposable = CompositeDisposable()
    private lateinit var movie: Movie

    override fun initialize(view: MovieDetailInteractor.View)
    {
        this.view = view
        this.view.initializeView()
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        outState.putParcelable(MOVIE, movie)
    }

    override fun onRestoreInstanceState(bundle: Bundle?, savedState: Boolean)
    {
        if(bundle != null)
        {
            movie = bundle.getParcelable(MOVIE)!!

            if(!savedState)
            {
                val disposable = repository.isFavorite(movie).subscribe { movie.isFavorite = it }
                composite.add(disposable)
            }

            view.putOnForm(movie)
        }
        else
        {
            view.showToast(R.string.movie_detail_not_found, Toast.LENGTH_LONG)
        }
    }

    override fun movieAction()
    {
        val disposable = repository.isFavorite(movie).subscribe { favorite ->
            if(favorite)
            {
                movieNotFavorite()
            }
            else
            {
                movieFavorite()
            }
        }

        composite.add(disposable)
    }

    private fun movieFavorite()
    {
        val disposable = repository.save(movie).subscribe({ view.movieSaveSuccess() }, { view.movieDeleteSuccess() })
        composite.add(disposable)
    }

    private fun movieNotFavorite()
    {
        val disposable = repository.delete(movie)
                .subscribe({ view.movieDeleteSuccess() }, { view.showToast(R.string.app_internal_error_client, Toast.LENGTH_LONG) })
        composite.add(disposable)
    }

    override fun terminate()
    {
        composite.clear()
    }
}