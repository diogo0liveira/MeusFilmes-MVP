package com.dao.mymovies.di

import android.content.Context
import com.dao.mymovies.MyMoviesApplication
import com.dao.mymovies.data.local.dao.MovieDAO
import com.dao.mymovies.data.local.database.MyMoviesDatabase
import com.dao.mymovies.di.annotations.ActivityScoped
import com.dao.mymovies.di.network.NetworkModule
import com.dao.mymovies.util.moshi.EmptyDateSafeAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import java.util.*
import javax.inject.Singleton

/**
 * Created in 26/03/19 22:08.
 *
 * @author Diogo Oliveira.
 */
@Module(includes = [NetworkModule::class])
class AppModule
{
    @Provides
    @ActivityScoped
    fun provideCompositeDisposable() = CompositeDisposable()

    @Provides
    @Singleton
    fun provideContext(application: MyMoviesApplication): Context = application.applicationContext

    @Provides
    @Singleton
    fun provideMovieDAO(context: Context): MovieDAO = MyMoviesDatabase.instance(context).movieDAO()

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(EmptyDateSafeAdapter)
            .add(Date::class.java, Rfc3339DateJsonAdapter().nullSafe()).build()
}
