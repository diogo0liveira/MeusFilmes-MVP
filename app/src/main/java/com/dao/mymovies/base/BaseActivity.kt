package com.dao.mymovies.base

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
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
    private var snackNotify: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    protected fun isNetworkConnected(): Boolean
    {
        val connectivity = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            val network = connectivity.activeNetwork
            val capabilities = connectivity.getNetworkCapabilities(network) ?: return false
            return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        }
        else
        {
            @Suppress("DEPRECATION")
            val networkInfo = connectivity.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnectedOrConnecting
        }
    }

    protected fun showSnackNotify(anchor: View, @StringRes text: Int, block: () -> Unit)
    {
        snackNotify = Snackbar.make(anchor, text, Snackbar.LENGTH_INDEFINITE)
        snackNotify?.run { setAction(R.string.retry) { block() }.show() }
    }

    protected fun removeSnackNotify()
    {
        snackNotify?.dismiss()
    }
}