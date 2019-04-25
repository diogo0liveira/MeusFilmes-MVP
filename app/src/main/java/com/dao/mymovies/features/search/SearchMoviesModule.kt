package com.dao.mymovies.features.search

import com.dao.mymovies.data.repository.MoviesRepository
import com.dao.mymovies.di.annotations.ActivityScoped
import com.dao.mymovies.util.SchedulerProvider
import dagger.Module
import dagger.Provides

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
    fun provideSearchMoviesPresenter(repository: MoviesRepository, schedulerProvider: SchedulerProvider): SearchMoviesInteractor.Presenter
    {
        return SearchMoviesPresenter(repository, schedulerProvider)
    }
}