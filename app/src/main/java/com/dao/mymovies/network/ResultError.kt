package com.dao.mymovies.network

import androidx.annotation.StringRes
import com.dao.mymovies.R
import com.diogo.oliveira.mymovies.util.network.Status
import com.google.gson.Gson
import okhttp3.ResponseBody
import retrofit2.Response

/**
 * Created in 03/08/18 13:20.
 *
 * @author Diogo Oliveira.
 */
class ResultError()
{
    private var code: Int = 0
    private var status: Status
    var messageRes: Int

    init
    {
        this.code = 0
        this.status = Status.INTERNAL_ERROR_CLIENT
        this.messageRes = R.string.app_internal_error_client
    }

    constructor(status: Status, @StringRes messageRes: Int) : this()
    {
        this.status = status
        this.messageRes = messageRes
    }

    companion object
    {
        fun <T> parse(response: Response<T>): ResultError
        {
            val body: ResponseBody = response.errorBody()!!
            return Gson().fromJson(body.string(), ResultError::class.java)
        }
    }
}