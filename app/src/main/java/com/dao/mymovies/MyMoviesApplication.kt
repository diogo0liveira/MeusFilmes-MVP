package com.dao.mymovies

import com.dao.mymovies.di.DaggerAppComponent
import com.dao.mymovies.util.Logger
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService

/**
 * Created in 24/04/19 09:44.
 *
 * @author Diogo Oliveira.
 */
//class MyMoviesApplication : Application(), HasActivityInjector
//{
//    @Inject
//    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>
//
//    override fun onCreate()
//    {
//        super.onCreate()
//        Logger.initialize(BuildConfig.DEBUG, TAG)
//        DaggerAppComponent.factory().create(this).inject(this)
//        BuildConfig.DEBUG.let { SQLiteStudioService.instance().start(this) }
//    }
//
//    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector
//}

open class MyMoviesApplication : DaggerApplication()
{
    lateinit var injector: AndroidInjector<MyMoviesApplication>

    override fun onCreate()
    {
        injector = DaggerAppComponent.factory().create(this)
        super.onCreate()

        Logger.initialize(BuildConfig.DEBUG, TAG)
        BuildConfig.DEBUG.let { SQLiteStudioService.instance().start(this) }
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication>
    {
        return injector
    }
}