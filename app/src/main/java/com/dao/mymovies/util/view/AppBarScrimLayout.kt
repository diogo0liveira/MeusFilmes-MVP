package com.dao.mymovies.util.view

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.animation.AnimationUtils
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

/**
 * Created in 30/04/19 14:42.
 *
 * @author Diogo Oliveira.
 */

class AppBarScrimLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
        AppBarLayout(context, attrs, defStyleAttr), AppBarLayout.OnOffsetChangedListener
{
    private var verticalOffset: Int = 0
    private var verticalOffsetMatch: Boolean = false

    var iconHomeIndicator: Drawable? = null
    var scrimAnimationDuration: Long = 600
    private var colorStart: Int = Color.BLACK
    private var colorEnd: Int = Color.WHITE

    override fun onAttachedToWindow()
    {
        super.onAttachedToWindow()
        addOnOffsetChangedListener(this)
    }

    override fun onDetachedFromWindow()
    {
        super.onDetachedFromWindow()
        removeOnOffsetChangedListener(this)
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int)
    {
        if(!verticalOffsetMatch && this.verticalOffset < abs(verticalOffset) && abs(verticalOffset) >= 198)
        {
            verticalOffsetMatch = true
            scrimChanged(true)
        }
        else if(verticalOffsetMatch && this.verticalOffset > abs(verticalOffset) && abs(verticalOffset) <= 197)
        {
            verticalOffsetMatch = false
            scrimChanged(false)
        }

        this.verticalOffset = abs(verticalOffset)
    }

    private fun scrimChanged(showing: Boolean)
    {
        iconHomeIndicator?.let { drawable ->
            val colors = if(showing) colorStart to colorEnd else colorEnd to colorStart

            val animator = ValueAnimator.ofArgb(colors.first, colors.second)
            animator.addUpdateListener { DrawableCompat.setTint(drawable, it.animatedValue as Int) }
            animator.interpolator = AnimationUtils.LINEAR_OUT_SLOW_IN_INTERPOLATOR
            animator.duration = scrimAnimationDuration
            animator.start()
        }
    }
}