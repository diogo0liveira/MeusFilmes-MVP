package com.dao.mymovies.base.mvp

/**
 * Created in 26/03/19 20:49.
 *
 * @author Diogo Oliveira.
 */
interface IPresenter<T : IView>
{
    fun initialize(view: T)
}
