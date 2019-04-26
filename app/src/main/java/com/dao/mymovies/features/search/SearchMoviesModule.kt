package com.dao.mymovies.features.search

import com.dao.mymovies.data.repository.MoviesRepository
import com.dao.mymovies.di.annotations.ActivityScoped
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Created in 17/08/18 11:16.
 *
 * @author Diogo Oliveira.
 */
@Module
class SearchMoviesModule
{
    @Provides
    @ActivityScoped
    fun provideSearchMoviesPresenter(repository: MoviesRepository, composite: CompositeDisposable): SearchMoviesInteractor.Presenter
    {
        return SearchMoviesPresenter(repository, composite)
    }
}