package com.dao.mymovies.network

import androidx.annotation.StringRes
import com.dao.mymovies.R

/**
 * Created in 02/04/19 12:06.
 *
 * @author Diogo Oliveira.
 */
enum class State
{
    RUNNING, SUCCESS, FAILED, UNAVAILABLE
}

data class NetworkState constructor(
        val status: State,
        val message: String? = null,
        val messageRes: Int = R.string.app_internal_server_unavailable,
        val retry: (() -> Unit)? = null)
{
    companion object
    {
        val RUNNING = NetworkState(State.RUNNING)
        val SUCCESS = NetworkState(State.SUCCESS)
        fun error(message: String?, retry: (() -> Unit)? = null) = NetworkState(State.FAILED, message, retry = retry)

        fun error(status: State = State.FAILED,
                  @StringRes message: Int,
                  retry: (() -> Unit)? = null) = NetworkState(status, messageRes = message, retry = retry)
    }
}