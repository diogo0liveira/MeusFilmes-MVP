package com.dao.mymovies

/**
 * Created in 26/03/19 22:26.
 *
 * @author Diogo Oliveira.
 */
const val TAG = "Meus Filmes"

object Extras
{
    const val MOVIE = "MOVIE"
    const val KEY_ORDER = "KEY_ORDER"
}

object KeyParameter
{
    const val LANGUAGE = "language"
    const val API_KEY = "api_key"
    const val QUERY = "query"
    const val PAGE = "page"
}

object TheMovieApi
{
    private const val IMAGE = "https://image.tmdb.org"
    const val URL = "https://api.themoviedb.org"
    const val KEY = BuildConfig.THEMOVIEDB_API_KEY

    const val SEARCH = ("$URL/3/search/movie")
    const val COVER = ("$IMAGE/t/p/w500")
}

object REQUEST
{
    const val REQUEST_SEARCH_MOVIES = 2000
    const val REQUEST_DETAIL_MOVIE = 2001
}