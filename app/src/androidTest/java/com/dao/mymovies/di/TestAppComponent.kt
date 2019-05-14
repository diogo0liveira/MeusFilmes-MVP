package com.dao.mymovies.di

import com.dao.mymovies.MyMoviesApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


/**
 * Created in 13/05/19 10:14.
 *
 * @author Diogo Oliveira.
 */
@Singleton
@Component(modules =
[
    AndroidSupportInjectionModule::class,
    TestActivityBindingModule::class,
    AppModule::class
])
interface TestAppComponent : AndroidInjector<MyMoviesApplication>
{
    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<MyMoviesApplication>
}