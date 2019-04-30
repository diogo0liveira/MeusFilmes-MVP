package com.dao.mymovies.util.view

import android.content.Context
import android.util.AttributeSet
import com.google.android.material.appbar.AppBarLayout

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
    private var scrimChangedListener: OnScrimChangedListener? = null

    override fun onDetachedFromWindow()
    {
        super.onDetachedFromWindow()
        removeOnOffsetChangedListener(this)
        scrimChangedListener = null
    }

    override fun onOffsetChanged(appBarLayout: AppBarLayout?, verticalOffset: Int)
    {
        if(!verticalOffsetMatch && this.verticalOffset < Math.abs(verticalOffset) && Math.abs(verticalOffset) >= 198)
        {
            verticalOffsetMatch = true
            scrimChangedListener?.onScrimChanged(true)
        }
        else if(verticalOffsetMatch && this.verticalOffset > Math.abs(verticalOffset) && Math.abs(verticalOffset) <= 197)
        {
            verticalOffsetMatch = false
            scrimChangedListener?.onScrimChanged(false)
        }

        this.verticalOffset = Math.abs(verticalOffset)
    }

    fun setOnScrimChangedListener(listener: OnScrimChangedListener)
    {
        scrimChangedListener = listener
        addOnOffsetChangedListener(this)
    }

    interface OnScrimChangedListener
    {
        fun onScrimChanged(showing: Boolean)
    }
}