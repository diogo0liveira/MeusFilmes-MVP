package com.dao.mymovies.features.detail

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.databinding.DataBindingUtil
import androidx.palette.graphics.Palette
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.dao.mymovies.R
import com.dao.mymovies.base.BaseActivity
import com.dao.mymovies.databinding.ActivityMovieDetailBinding
import com.dao.mymovies.model.Movie
import com.dao.mymovies.util.Logger
import com.dao.mymovies.util.extensions.contrastColor
import com.dao.mymovies.util.extensions.drawable
import org.jetbrains.anko.longToast
import org.jetbrains.anko.toast
import javax.inject.Inject


/**
 * Created in 03/08/18 15:59.
 *
 * @author Diogo Oliveira.
 */
class MovieDetailActivity : BaseActivity(), MovieDetailInteractor.View, View.OnClickListener
{
    @Inject
    lateinit var presenter: MovieDetailInteractor.Presenter

    private lateinit var helper: ActivityMovieDetailBinding
    private lateinit var drawableHomeIndicator: Drawable

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        helper = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        presenter.initialize(this)

        if(savedInstanceState != null)
        {
            presenter.onRestoreInstanceState(savedInstanceState, true)
        }
        else
        {
            presenter.onRestoreInstanceState(this.intent.extras, false)
        }
    }

    override fun onSaveInstanceState(outState: Bundle)
    {
        presenter.onSaveInstanceState(outState)
        super.onSaveInstanceState(outState)
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

    override fun onDestroy()
    {
        presenter.terminate()
        super.onDestroy()
    }

    override fun onClick(view: View)
    {
        when(view.id)
        {
            R.id.button_favorite ->
            {
                presenter.movieAction()
            }
        }
    }

    override fun initializeView()
    {
        setSupportActionBar(helper.toolbar)
        drawableHomeIndicator = drawable(R.drawable.vd_arrow_back_24dp)
        DrawableCompat.setTint(drawableHomeIndicator, Color.WHITE)

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(drawableHomeIndicator)
        }

        helper.buttonFavorite.setOnClickListener(this)
    }

    override fun putOnForm(movie: Movie)
    {
        helper.requestListener = setColorHomeIndicator()
        helper.movie = movie
    }

    override fun changeMovieFavoriteSuccess()
    {
        setResult(Activity.RESULT_OK)
    }

    override fun showToast(text: Int, duration: Int)
    {
        when(duration)
        {
            Toast.LENGTH_SHORT -> toast(text)
            Toast.LENGTH_LONG -> longToast(text)
        }
    }

    private fun setColorHomeIndicator(): RequestListener<Bitmap>
    {
        return object : RequestListener<Bitmap>
        {
            override fun onResourceReady(resource: Bitmap?, model: Any?, target: Target<Bitmap>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean
            {
                resource?.let { bitmap ->
                    Palette.from(Bitmap.createBitmap(bitmap, 0, 0, 100, 100)).generate { palette ->
                        palette?.let { setScrimColorHomeIndicator(it.contrastColor()) }
                    }
                }
                return false
            }

            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Bitmap>?, isFirstResource: Boolean): Boolean
            {
                e?.also { Logger.e(it) }
                return false
            }
        }
    }

    private fun setScrimColorHomeIndicator(@ColorInt color: Int)
    {
        if(color == Color.BLACK)
        {
            helper.toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.text_primary_light))
            DrawableCompat.setTint(drawableHomeIndicator, Color.BLACK)

            helper.appBar.scrimAnimationDuration = helper.toolbarLayout.scrimAnimationDuration
            helper.appBar.iconHomeIndicator = drawableHomeIndicator
        }
    }
}