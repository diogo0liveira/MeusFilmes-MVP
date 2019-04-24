package com.dao.mymovies.base

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dao.mymovies.R
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection


/**
 * Created in 26/03/19 20:49.
 *
 * @author Diogo Oliveira.
 */
@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity()
{
    private var snackOffline: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    protected fun isNetworkConnected(): Boolean
    {
        val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return (manager.activeNetworkInfo != null) && manager.activeNetworkInfo.isConnected
    }

    protected fun notifyDisconnected(anchor: View, block: () -> Unit)
    {
        snackOffline = Snackbar.make(anchor, R.string.app_internal_no_connection, Snackbar.LENGTH_INDEFINITE)

        snackOffline?.run {
            setAction(R.string.retry) { block() }.show()
        }
    }

    protected fun removeNotifyDisconnected()
    {
        snackOffline?.dismiss()
    }
}