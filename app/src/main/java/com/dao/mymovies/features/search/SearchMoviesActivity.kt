package com.dao.mymovies.features.search

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.dao.mymovies.Extras.MOVIE
import com.dao.mymovies.R
import com.dao.mymovies.REQUEST.REQUEST_DETAIL_MOVIE
import com.dao.mymovies.base.BaseActivity
import com.dao.mymovies.base.OnCollectionChangedListener
import com.dao.mymovies.databinding.ActivitySearchMoviesBinding
import com.dao.mymovies.databinding.ViewEmptySearchMoviesBinding
import com.dao.mymovies.features.detail.MovieDetailActivity
import com.dao.mymovies.features.list.MyMoviesAdapter
import com.dao.mymovies.model.Movie
import org.jetbrains.anko.longToast
import org.jetbrains.anko.startActivityForResult
import org.jetbrains.anko.toast
import javax.inject.Inject

/**
 * Created in 03/08/18 16:00.
 *
 * @author Diogo Oliveira.
 */
class SearchMoviesActivity : BaseActivity(), SearchMoviesInteractor.View, OnCollectionChangedListener, MyMoviesAdapter.MovieViewOnClickListener
{
    @Inject
    lateinit var presenter: SearchMoviesInteractor.Presenter
    private lateinit var helperEmpty: ViewEmptySearchMoviesBinding
    private lateinit var helper: ActivitySearchMoviesBinding
    private lateinit var searchView: SearchView

    private val adapter: MyMoviesAdapter by lazy { MyMoviesAdapter(this, this) }

    override fun onCreate(@Nullable savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        helper = DataBindingUtil.setContentView(this, R.layout.activity_search_movies)
        presenter.initialize(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        menuInflater.inflate(R.menu.option_search_movies, menu)
        val item = menu.findItem(R.id.action_search)
        searchView = item.actionView as SearchView

        searchView.setIconifiedByDefault(true)
        searchView.isIconified = false

        searchView.maxWidth = Integer.MAX_VALUE
        searchView.onActionViewExpanded()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener
        {
            override fun onQueryTextSubmit(query: String?): Boolean
            {
                query?.let {
                    helperEmpty.visible = true
                    helperEmpty.showProgressBar = true
                    presenter.searchMovies(it)
                    searchView.clearFocus()
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean
            {
                newText?.let { adapter.clean() }
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        return when(item.itemId)
        {
            android.R.id.home ->
            {
                finish()
                true
            }
            else ->
            {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?)
    {
        when(requestCode)
        {
            REQUEST_DETAIL_MOVIE ->
            {
                if(resultCode == Activity.RESULT_OK)
                {
                    setResult(Activity.RESULT_OK)
                }
            }
            else ->
            {
                super.onActivityResult(requestCode, resultCode, intent)
            }
        }
    }

    override fun context(): Context
    {
        return this
    }

    override fun initializeView()
    {
        helper.viewState.viewStub?.inflate()?.let { view ->
            DataBindingUtil.getBinding<ViewEmptySearchMoviesBinding>(view)?.let { binding ->
                helperEmpty = binding
                helperEmpty.visible = true
            }
        }

        with(helper.searchList) {
            val divider = DividerItemDecoration(this@SearchMoviesActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(divider)
            setHasFixedSize(true)
        }

        adapter.setOnCollectionChangedListener(this)
        helper.searchList.adapter = adapter

        presenter.searchObserver().observe(this, Observer { adapter.submitList(it) })
    }

    override fun onCollectionChanged(isEmpty: Boolean)
    {
        helperEmpty.visible = isEmpty
        helperEmpty.showProgressBar = false
        helper.progressPagination.visibility = View.GONE
    }

    override fun onMovieViewOnClick(movie: Movie)
    {
        startActivityForResult<MovieDetailActivity>(REQUEST_DETAIL_MOVIE, Pair(MOVIE, movie))
    }

    override fun showToast(text: Int, duration: Int)
    {
        when(duration)
        {
            Toast.LENGTH_SHORT -> toast(text)
            Toast.LENGTH_LONG -> longToast(text)
        }
    }

    override fun changeSearchProgress(visible: Boolean)
    {
        helperEmpty.visible = visible
        helperEmpty.showProgressBar = visible
    }

    override fun changePaginationProgress(visible: Boolean)
    {
        helper.progressPagination.visibility = (if(visible) View.VISIBLE else View.GONE)
    }
}