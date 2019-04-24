package com.dao.mymovies.di.network

import android.content.Context
import com.dao.mymovies.BuildConfig
import com.dao.mymovies.TheMovieApi
import com.dao.mymovies.network.TheMovies
import com.dao.mymovies.network.interceptor.TokenRequestInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created in 26/03/19 22:02.
 *
 * @author Diogo Oliveira.
 */
@Module
class NetworkModule
{
    @Provides
    @Singleton
    fun provideCache(context: Context): Cache = Cache(context.cacheDir, 5 * 1024 * 1024)

    @Provides
    @Singleton
    fun provideInterceptor(): Interceptor =
            HttpLoggingInterceptor().apply {
                level = if(BuildConfig.DEBUG) Level.BODY else Level.NONE
            }

    @Provides
    @Singleton
    fun provideHttpClient(interceptor: Interceptor, cache: Cache): OkHttpClient =
            OkHttpClient
                    .Builder()
                    .addInterceptor(TokenRequestInterceptor())
                    .addNetworkInterceptor(interceptor)
                    .cache(cache)
                    .build()
    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient, gson: Gson): Retrofit =
            Retrofit.Builder()
                    .client(httpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .baseUrl(TheMovieApi.URL).build()


    @Provides
    @Singleton
    fun provideNetworkService(retrofit: Retrofit): TheMovies
    {
        return retrofit.create(TheMovies::class.java)
    }
}