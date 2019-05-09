package com.dao.mymovies.features.detail

import android.os.Bundle
import android.widget.Toast
import com.dao.mymovies.Extras.MOVIE
import com.dao.mymovies.R
import com.dao.mymovies.data.MovieRepository
import com.dao.mymovies.model.Movie
import com.dao.mymovies.util.withSchedulers
import io.reactivex.CompletableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer

/**
 * Created in 03/08/18 16:59.
 *
 * @author Diogo Oliveira.
 */
class MovieDetailPresenter(
        private val repository: MovieRepository,
        private val composite: CompositeDisposable) : MovieDetailInteractor.Presenter
{
    private lateinit var view: MovieDetailInteractor.View
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
                isFavorite { favorite -> movie.isFavorite.set(favorite) }
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
        isFavorite { favorite -> if(favorite) movieNotFavorite() else movieFavorite() }
    }

    private fun movieFavorite()
    {
      val disposable = repository.save(movie)
              .compose(withSchedulers<CompletableTransformer>())
              .subscribe({ changeMovieFavorite(true) },
                         { view.showToast(R.string.app_internal_error_client, Toast.LENGTH_LONG) })

        composite.add(disposable)
    }

    private fun movieNotFavorite()
    {
        val disposable = repository.delete(movie)
                .compose(withSchedulers<CompletableTransformer>())
                .subscribe({changeMovieFavorite(false)},
                           {view.showToast(R.string.app_internal_error_client, Toast.LENGTH_LONG)})

        composite.add(disposable)
    }

    private fun changeMovieFavorite(favorite: Boolean)
    {
        movie.isFavorite.set(favorite)
        view.changeMovieFavoriteSuccess()
    }

    private fun isFavorite(block: (favorite: Boolean) -> Unit)
    {
        val consumer = Consumer<Boolean> { block(it) }
        val error = Consumer<Throwable>  { view.showToast(R.string.app_internal_error_client, Toast.LENGTH_LONG) }

        val disposable = repository.isFavorite(movie)
                .compose(withSchedulers<SingleTransformer<Boolean, Boolean>>())
                .subscribe(consumer, error)

        composite.add(disposable)
    }

    override fun terminate()
    {
        composite.clear()
    }
}