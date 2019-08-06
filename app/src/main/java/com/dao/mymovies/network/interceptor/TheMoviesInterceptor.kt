package com.dao.mymovies.network.interceptor

import com.dao.mymovies.KeyParameter
import com.dao.mymovies.TheMovieApi
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Created in 25/04/19 09:00.
 *
 * @author Diogo Oliveira.
 */
class TheMoviesInterceptor : Interceptor
{
    override fun intercept(chain: Interceptor.Chain): Response
    {
        if(chain.request().url.toString().startsWith(TheMovieApi.SEARCH))
        {
            val request: Request = chain.request()
            val url: HttpUrl = request.url.newBuilder().addQueryParameter(
                    KeyParameter.LANGUAGE, "pt-BR&append_to_response=images&include_image_language=pt-BR,null").build()

            val requestBuilder: Request.Builder = request.newBuilder().url(url)
            return chain.proceed(requestBuilder.build())
        }

        return chain.proceed(chain.request())
    }
}