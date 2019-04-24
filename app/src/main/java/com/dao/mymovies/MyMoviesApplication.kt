package com.dao.mymovies

import android.app.Activity
import android.app.Application
import com.dao.mymovies.di.DaggerAppComponent
import com.dao.mymovies.util.Logger
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import pl.com.salsoft.sqlitestudioremote.SQLiteStudioService
import javax.inject.Inject

/**
 * Created in 24/04/19 09:44.
 *
 * @author Diogo Oliveira.
 */
class MyMoviesApplication : Application(), HasActivityInjector
{
    @Inject
    lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate()
    {
        super.onCreate()
        Logger.initialize(BuildConfig.DEBUG, TAG)
        DaggerAppComponent.factory().create(this).inject(this)
        BuildConfig.DEBUG.let { SQLiteStudioService.instance().start(this) }
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector
}