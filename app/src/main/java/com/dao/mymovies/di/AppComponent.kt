package com.dao.mymovies.di

import com.dao.mymovies.MyMoviesApplication
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created in 26/03/19 22:06.
 *
 * @author Diogo Oliveira.
 */
@Singleton
@Component(modules =
[
    AndroidSupportInjectionModule::class,
    ActivityBindingModule::class,
    AppModule::class
])
interface AppComponent : AndroidInjector<MyMoviesApplication>
{
    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<MyMoviesApplication>
}