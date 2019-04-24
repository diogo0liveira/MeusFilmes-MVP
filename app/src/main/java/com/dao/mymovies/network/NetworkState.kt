package com.dao.mymovies.network

/**
 * Created in 02/04/19 12:06.
 *
 * @author Diogo Oliveira.
 */
enum class State
{
    RUNNING, SUCCESS, FAILED
}

data class NetworkState constructor(val status: State, val message: String? = null, val retry: (() -> Unit)? = null)
{
    companion object
    {
        val RUNNING = NetworkState(State.RUNNING)
        val SUCCESS = NetworkState(State.SUCCESS)
        fun error(message: String?, retry: (() -> Unit)? = null) = NetworkState(State.FAILED, message, retry)
    }
}