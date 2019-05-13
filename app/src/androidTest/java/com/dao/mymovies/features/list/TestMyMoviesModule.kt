@file:Suppress("unused")

package com.dao.mymovies.features.list

import com.dao.mymovies.data.repository.FakeRepositoryModule
import dagger.Binds
import dagger.Module

/**
 * Created in 15/08/18 17:04.
 *
 * @author Diogo Oliveira.
 */
@Module(includes = [FakeRepositoryModule::class])
interface TestMyMoviesModule
{
    @Binds
    fun provideTestMyMoviesPresenter(presenter: MyMoviesPresenter): MyMoviesInteractor.Presenter
}