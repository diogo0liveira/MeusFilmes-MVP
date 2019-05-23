@file:Suppress("unused")

package com.dao.mymovies.di

import com.dao.mymovies.di.annotations.ActivityScoped
import com.dao.mymovies.features.detail.MovieDetailActivity
import com.dao.mymovies.features.detail.TestMovieDetailModule
import com.dao.mymovies.features.list.MyMoviesActivity
import com.dao.mymovies.features.list.TestMyMoviesModule
import com.dao.mymovies.features.search.SearchMoviesActivity
import com.dao.mymovies.features.search.TestSearchMoviesModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created in 13/05/19 10:18.
 *
 * @author Diogo Oliveira.
 */
@Module
abstract class TestActivityBindingModule
{
    @ActivityScoped
    @ContributesAndroidInjector(modules = [TestMyMoviesModule::class])
    abstract fun bindTestMyMoviesActivity(): MyMoviesActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [TestSearchMoviesModule::class])
    abstract fun bindTestSearchMoviesActivity(): SearchMoviesActivity

    @ActivityScoped
    @ContributesAndroidInjector(modules = [TestMovieDetailModule::class])
    abstract fun bindTestMovieDetailActivity(): MovieDetailActivity
}