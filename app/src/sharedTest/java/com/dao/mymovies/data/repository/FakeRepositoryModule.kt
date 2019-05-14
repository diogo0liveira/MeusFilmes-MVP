package com.dao.mymovies.data.repository

import com.dao.mymovies.data.MovieRepository
import com.dao.mymovies.data.local.MovieLocalDataSource
import com.dao.mymovies.data.remote.MovieRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * Created in 13/05/19 10:07.
 *
 * @author Diogo Oliveira.
 */
//@Module(includes = [FakeLocalDataSourceModule::class, FakeRemoteDataSourceModule::class])
//class FakeRepositoryModule
//{
//    @Provides
//    fun provideFakeMovieRepository(local: MovieLocalDataSource, remote: MovieRemoteDataSource): MovieRepository
//    {
//        return FakeMovieRepository(local, remote)
//    }
//}

@Module
class FakeRepositoryModule(local: MovieLocalDataSource, remote: MovieRemoteDataSource)
{
    init
    {
        Factory.build(local, remote)
    }

    @Module
    companion object
    {
        @Provides
        @Reusable
        @JvmStatic
        fun provideFakeMovieRepository(): MovieRepository
        {
            return Factory.get()
        }
    }

    object Factory
    {
        private lateinit var local: MovieLocalDataSource
        private lateinit var remote: MovieRemoteDataSource

        fun build(local: MovieLocalDataSource, remote: MovieRemoteDataSource)
        {
            this.local = local
            this.remote = remote
        }

        fun get() = FakeMovieRepository(local, remote)
    }
}

