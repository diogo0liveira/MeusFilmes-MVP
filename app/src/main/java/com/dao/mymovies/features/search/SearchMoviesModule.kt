package com.dao.mymovies.features.search

import com.dao.mymovies.data.MovieRepository
import com.dao.mymovies.data.repository.RepositoryModule
import com.dao.mymovies.di.annotations.ActivityScoped
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Created in 17/08/18 11:16.
 *
 * @author Diogo Oliveira.
 */
@Module(includes = [RepositoryModule::class])
class SearchMoviesModule
{
    @Provides
    @ActivityScoped
    fun provideSearchMoviesPresenter(repository: MovieRepository, composite: CompositeDisposable): SearchMoviesInteractor.Presenter
    {
        return SearchMoviesPresenter(repository, composite)
    }
}