package com.dao.mymovies.util

import io.reactivex.ObservableTransformer
import io.reactivex.Scheduler


/**
 * Created in 29/03/19 08:31.
 *
 * @author Diogo Oliveira.
 */
class SchedulerProvider(
        private val backgroundScheduler: Scheduler,
        private val foregroundScheduler: Scheduler)
{
    fun <T> applySchedulers(): ObservableTransformer<T, T>
    {
        return ObservableTransformer { observable ->
            observable.subscribeOn(backgroundScheduler).observeOn(foregroundScheduler)
        }
    }
}