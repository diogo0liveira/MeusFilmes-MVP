package com.dao.mymovies.di

import android.content.Context
import com.dao.mymovies.MyMoviesApplication
import com.dao.mymovies.data.local.dao.MovieDAO
import com.dao.mymovies.data.local.database.MyMoviesDatabase
import com.dao.mymovies.di.annotations.ActivityScoped
import com.dao.mymovies.di.network.NetworkModule
import com.dao.mymovies.util.SchedulerProvider
import com.dao.mymovies.util.json.DateDeserializer
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.text.DateFormat
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
    fun provideSchedulerProvider() = SchedulerProvider(Schedulers.io(), AndroidSchedulers.mainThread())

    @Provides
    @Singleton
    fun provideMovieDAO(context: Context): MovieDAO = MyMoviesDatabase.instance(context).movieDAO()

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
            .setDateFormat("dd/MM/yyyy")
            .registerTypeAdapter(Date::class.java, DateDeserializer())
            .create()
}
