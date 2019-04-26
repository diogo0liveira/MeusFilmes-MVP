package com.dao.mymovies.util

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created in 29/03/19 08:31.
 *
 * @author Diogo Oliveira.
 */
inline fun <reified T> withSchedulers(): T
{
    when(T::class)
    {
        ObservableTransformer::class -> return ObservableTransformer<T, T> {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        } as T
        SingleTransformer::class -> return SingleTransformer<T, T> {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        } as T
        FlowableTransformer::class -> return FlowableTransformer<T, T> {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        } as T
        CompletableTransformer::class -> return CompletableTransformer {
            it.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        } as T
    }

    throw IllegalArgumentException("not a valid Transformer type")
}