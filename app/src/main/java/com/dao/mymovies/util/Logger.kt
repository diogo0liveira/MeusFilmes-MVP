@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package com.dao.mymovies.util

import android.util.Log

/**
 * Created in 24/04/19 09:55.
 *
 * @author Diogo Oliveira.
 */
object Logger
{
    private var tag: String
    private var printable: Boolean

    init
    {
        tag = ""
        printable = false
    }

    fun initialize(debugger: Boolean, tag: String = "")
    {
        this.tag = tag
        this.printable = debugger
    }

    fun v(message: String)
    {
        v(tag, message)
    }

    fun v(tag: String, message: String)
    {
        print(message) { Log.v(target(tag), it) }
    }

    fun d(message: String)
    {
        d(tag, message)
    }

    fun d(tag: String, message: String)
    {
        print(message) { Log.d(target(tag), it) }
    }

    fun i(message: String)
    {
        i(tag, message)
    }

    fun i(tag: String, message: String)
    {
        print(message) { Log.i(target(tag), it) }
    }

    fun w(message: String)
    {
        w(tag, message)
    }

    fun w(tag: String, message: String)
    {
        print(message) { Log.w(target(tag), it) }
    }

    fun e(message: String)
    {
        e(tag, message)
    }

    fun e(throwable: Throwable)
    {
        e(tag, throwable.message.orEmpty())
    }

    fun e(tag: String, message: String)
    {
        print(message) { Log.e(target(tag), it) }
    }

    fun e(tag: String, message: String, throwable: Throwable)
    {
        print(message) { Log.e(target(tag), it, throwable) }
    }

    private fun print(message: String, log: (msg: String) -> Int)
    {
        if(printable) log.invoke(message)
    }

    private fun target(tag: String): String
    {
        return (if(tag.isNotEmpty()) tag else this.tag)
    }
}