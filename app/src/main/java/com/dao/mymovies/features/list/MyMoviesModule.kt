package com.dao.mymovies.features.list

import com.dao.mymovies.data.repository.MoviesRepository
import com.dao.mymovies.di.annotations.ActivityScoped
import dagger.Module
import dagger.Provides


/**
 * Created in 15/08/18 17:04.
 *
 * @author Diogo Oliveira.
 */
@Module
class MyMoviesModule
{
    @Provides
    @ActivityScoped
    fun provideMyMoviesPresenter(repository: MoviesRepository): MyMoviesInteractor.Presenter
    {
        return MyMoviesPresenter(repository)
    }
}