@file:Suppress("unused")

package com.dao.mymovies.features.list

import com.dao.mymovies.data.repository.RepositoryModule
import dagger.Binds
import dagger.Module

/**
 * Created in 15/08/18 17:04.
 *
 * @author Diogo Oliveira.
 */
@Module(includes = [RepositoryModule::class])
interface MyMoviesModule
{
    @Binds
    fun provideMyMoviesPresenter(presenter: MyMoviesPresenter): MyMoviesInteractor.Presenter
}