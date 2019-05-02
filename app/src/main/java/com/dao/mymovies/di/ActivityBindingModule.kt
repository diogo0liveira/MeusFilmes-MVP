@file:Suppress("unused")

package com.dao.mymovies.di

import com.dao.mymovies.di.annotations.ActivityScoped
import com.dao.mymovies.features.detail.MovieDetailActivity
import com.dao.mymovies.features.detail.MovieDetailModule
import com.dao.mymovies.features.list.MyMoviesActivity
import com.dao.mymovies.features.list.MyMoviesModule
import com.dao.mymovies.features.search.SearchMoviesActivity
import com.dao.mymovies.features.search.SearchMoviesModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created in 26/03/19 22:05.
 *
 * @author Diogo Oliveira.
 */
@Module
abstract class ActivityBindingModule
{
    @ActivityScoped
    @ContributesAndroidInjector(modules = [MyMoviesModule::class])
    abstract fun bindMyMoviesActivity(): MyMoviesActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [SearchMoviesModule::class])
    abstract fun bindSearchMoviesActivity(): SearchMoviesActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [MovieDetailModule::class])
    abstract fun bindMovieDetailActivity(): MovieDetailActivity
}