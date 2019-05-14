package com.dao.mymovies.data.repository

import com.dao.mymovies.RepositoryFactory
import com.dao.mymovies.data.MovieRepository
import dagger.Module
import dagger.Provides

/**
 * Created in 13/05/19 10:07.
 *
 * @author Diogo Oliveira.
 */
@Module
class FakeRepositoryModule
{
    @Provides
    fun provideFakeMovieRepository(): MovieRepository = RepositoryFactory.repository
}
