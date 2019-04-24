package com.dao.mymovies.features.search.paging

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dao.mymovies.data.repository.MoviesRepository
import com.dao.mymovies.model.Movie
import com.dao.mymovies.util.SchedulerProvider
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
        private val schedulerProvider: SchedulerProvider,
        private val repository: MoviesRepository): DataSource.Factory<Int, Movie>()
{
    val source = MutableLiveData<SearchPageKeyedDataSource>()

    override fun create(): DataSource<Int, Movie>
    {
        val issuesDataSource = SearchPageKeyedDataSource(query, composite, schedulerProvider, repository)
        this.source.postValue(issuesDataSource)
        return issuesDataSource
    }
}