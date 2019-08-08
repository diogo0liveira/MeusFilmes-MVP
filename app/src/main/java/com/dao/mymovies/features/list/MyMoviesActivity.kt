package com.dao.mymovies.features.list

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.dao.mymovies.Extras.KEY_ORDER
import com.dao.mymovies.Extras.MOVIE
import com.dao.mymovies.R
import com.dao.mymovies.REQUEST.REQUEST_DETAIL_MOVIE
import com.dao.mymovies.REQUEST.REQUEST_SEARCH_MOVIES
import com.dao.mymovies.SplashScreen
import com.dao.mymovies.databinding.ActivityMyMoviesBinding
import com.dao.mymovies.databinding.ViewEmptyMyMoviesBinding
import com.dao.mymovies.features.adapter.MyMoviesAdapter
import com.dao.mymovies.features.detail.MovieDetailActivity
import com.dao.mymovies.features.search.SearchMoviesActivity
import com.dao.mymovies.model.Movie
import com.dao.mymovies.model.Order
import com.dao.mymovies.util.extensions.getOrder
import com.dao.mymovies.util.extensions.getParcelable
import org.jetbrains.anko.startActivityForResult
import javax.inject.Inject

/**
 * Created in 27/07/18 09:50.
 *
 * @author Diogo Oliveira.
 */
class MyMoviesActivity : SplashScreen(), MyMoviesInteractor.View, View.OnClickListener
{
    @Inject
    lateinit var presenter: MyMoviesInteractor.Presenter
    private lateinit var helperEmpty: ViewEmptyMyMoviesBinding
    private lateinit var helper: ActivityMyMoviesBinding
    private lateinit var order: Order

    private val adapter: MyMoviesAdapter by lazy { MyMoviesAdapter(this, this) }
    private val preferences: SharedPreferences by lazy { this.getPreferences(Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        helper = DataBindingUtil.setContentView(this, R.layout.activity_my_movies)
        order = savedInstanceState.getParcelable(KEY_ORDER, getOrder())

        presenter.moviesOrderBy(order)
        presenter.initialize(this)
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        outState.putParcelable(KEY_ORDER, order)
        super.onSaveInstanceState(outState)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean
    {
        menu.findItem(R.id.menu_order)?.let {
            when(this.order)
            {
                Order.TITLE ->
                {
                    it.subMenu.findItem(R.id.menu_order_title).isChecked = true
                }
                Order.DATE ->
                {
                    it.subMenu.findItem(R.id.menu_order_date).isChecked = true
                }
            }
        }

        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean
    {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_my_movies, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean
    {
        when(item.itemId)
        {
            R.id.menu_order_title ->
            {
                item.isChecked = true
                sortList(Order.TITLE)
                return true
            }
            R.id.menu_order_date ->
            {
                item.isChecked = true
                sortList(Order.DATE)
                return true
            }
            else ->
            {
                return super.onOptionsItemSelected(item)
            }
        }
    }

    override fun initializeView()
    {
        helper.buttonAdd.setOnClickListener(this)

        helper.messageEmpty.viewStub?.inflate()?.let { view ->
            DataBindingUtil.getBinding<ViewEmptyMyMoviesBinding>(view)?.let { binding ->
                helperEmpty = binding
                helperEmpty.visible = true
            }
        }

        with(helper.moviesList) {
            val divider = DividerItemDecoration(this@MyMoviesActivity, DividerItemDecoration.VERTICAL)
            addItemDecoration(divider)
            setHasFixedSize(true)
        }

        adapter.setOnCollectionChangedListener(this)
        helper.moviesList.adapter = adapter

        presenter.moviesObserver().observe(this, Observer { adapter.submitList(it) })
    }

    override fun onCollectionChanged(isEmpty: Boolean)
    {
        helperEmpty.visible = isEmpty
    }

    override fun onClick(view: View)
    {
        when(view.id)
        {
            R.id.button_add ->
            {
                startSearchMoviesActivity()
            }
        }
    }

    override fun onMovieViewOnClick(movie: Movie)
    {
        startActivityForResult<MovieDetailActivity>(REQUEST_DETAIL_MOVIE, Pair(MOVIE, movie))
    }

    override fun startSearchMoviesActivity()
    {
        startActivityForResult<SearchMoviesActivity>(REQUEST_SEARCH_MOVIES)
    }

    private fun sortList(order: Order)
    {
        this.order = order
        presenter.moviesOrderBy(order)
        saveOrder(order)
    }

    private fun saveOrder(order: Order)
    {
        preferences.edit().putString(KEY_ORDER, order.name).apply()
    }

    private fun getOrder(): Order
    {
        return preferences.getOrder(KEY_ORDER, Order.TITLE)
    }
}