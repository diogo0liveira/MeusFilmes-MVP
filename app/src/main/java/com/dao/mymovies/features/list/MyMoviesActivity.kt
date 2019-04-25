package com.dao.mymovies.features.list

import android.content.Context
import android.content.Intent
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
import com.dao.mymovies.base.OnCollectionChangedListener
import com.dao.mymovies.databinding.ActivityMyMoviesBinding
import com.dao.mymovies.databinding.ViewEmptyMyMoviesBinding
import com.dao.mymovies.features.adapter.MyMoviesAdapter
import com.dao.mymovies.features.detail.MovieDetailActivity
import com.dao.mymovies.features.search.SearchMoviesActivity
import com.dao.mymovies.model.Movie
import com.dao.mymovies.model.Order
import org.jetbrains.anko.startActivityForResult
import javax.inject.Inject

/**
 * Created in 27/07/18 09:50.
 *
 * @author Diogo Oliveira.
 */
class MyMoviesActivity : SplashScreen(), MyMoviesInteractor.View, View.OnClickListener, OnCollectionChangedListener, MyMoviesAdapter.MovieViewOnClickListener
{
    @Inject
    lateinit var presenter: MyMoviesInteractor.Presenter
    private lateinit var helperEmpty: ViewEmptyMyMoviesBinding
    private lateinit var helper: ActivityMyMoviesBinding
    private lateinit var order: Order

    private val adapter: MyMoviesAdapter by lazy {
        MyMoviesAdapter(this, this)
    }

    private val preferences by lazy {
        this.getPreferences(Context.MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        helper = DataBindingUtil.setContentView(this, R.layout.activity_my_movies)
        presenter.initialize(this)

        if(savedInstanceState == null)
        {
            order = getOrder()
        }
        else
        {
            order = savedInstanceState.getParcelable(KEY_ORDER)!!
        }
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        outState.putParcelable(KEY_ORDER, order)
        super.onSaveInstanceState(outState)
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean
    {
        val order = menu.findItem(R.id.menu_order)

        order?.let {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        when(requestCode)
        {
            REQUEST_DETAIL_MOVIE, REQUEST_SEARCH_MOVIES ->
            {
                if(resultCode == RESULT_OK)
                {
//                    presenter.loadMyMovieList()
                }
            }
            else ->
            {
                super.onActivityResult(requestCode, resultCode, intent)
            }
        }
    }

    override fun initializeView()
    {
        helper.buttonAdd.setOnClickListener(this)

        if(helper.messageEmpty.isInflated)
        {
            helperEmpty.visible = true
        }
        else
        {
            helper.messageEmpty.viewStub!!.visibility = View.VISIBLE
            helperEmpty = DataBindingUtil.findBinding(helper.messageEmpty.root)!!
            helperEmpty.visible = true
        }

        val divider = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        helper.moviesList.addItemDecoration(divider)
        helper.moviesList.setHasFixedSize(true)

        adapter.setOnCollectionChangedListener(this)
        helper.moviesList.adapter = adapter

        presenter.moviesObserver().observe(this, Observer {
            adapter.submitList(it)
            sortList(order)
        })
    }

    override fun onCollectionChanged(isEmpty: Boolean)
    {
        helperEmpty.visible = isEmpty
    }

    override fun context(): Context
    {
        return this
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
        saveOrder(order)

//        when(order)
//        {
//            Order.TITLE ->
//            {
//                adapter.sort { it -> it.title }
//            }
//            Order.DATE ->
//            {
//                adapter.sort(true) { it -> it.releaseDate }
//            }
//        }
    }

    private fun saveOrder(order: Order)
    {
        preferences.edit().putString(KEY_ORDER, order.name).apply()
    }

    private fun getOrder(): Order
    {
        return Order.valueOf(preferences.getString(KEY_ORDER, Order.TITLE.name)!!)
    }
}