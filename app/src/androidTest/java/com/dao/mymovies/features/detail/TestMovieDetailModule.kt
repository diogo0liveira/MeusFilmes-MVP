package com.dao.mymovies.features.detail

import com.dao.mymovies.data.MovieRepository
import com.dao.mymovies.data.repository.FakeRepositoryModule
import com.dao.mymovies.di.annotations.ActivityScoped
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Created in 17/08/18 11:36.
 *
 * @author Diogo Oliveira.
 */
@Module(includes = [FakeRepositoryModule::class])
class TestMovieDetailModule
{
    @Provides
    @ActivityScoped
    fun provideTestMovieDetailPresenter(repository: MovieRepository, composite: CompositeDisposable): MovieDetailInteractor.Presenter
    {
        return MovieDetailPresenter(repository, composite)
    }
}