package com.dao.mymovies.features.search.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dao.mymovies.data.MovieRepository
import com.dao.mymovies.model.Movie
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created in 02/04/19 11:30.
 *
 * @author Diogo Oliveira.
 */
class SearchDataSourceFactory @Inject constructor(
        private val query: String,
        private val composite: CompositeDisposable,
        private val repository: MovieRepository): DataSource.Factory<Int, Movie>()
{
    val source = MutableLiveData<SearchPageKeyedDataSource>()

    override fun create(): DataSource<Int, Movie>
    {
        val issuesDataSource = SearchPageKeyedDataSource(query, composite, repository)
        this.source.postValue(issuesDataSource)
        return issuesDataSource
    }
}