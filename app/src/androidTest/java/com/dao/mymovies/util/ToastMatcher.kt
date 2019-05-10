package com.dao.mymovies.util

import android.view.WindowManager
import androidx.test.espresso.Root
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

/**
 * Created in 10/05/19 12:27.
 *
 * @author Diogo Oliveira.
 */
class ToastMatcher : TypeSafeMatcher<Root>()
{
    override fun describeTo(description: Description)
    {
        description.appendText("is toast")
    }

    @Suppress("DEPRECATION")
    override fun matchesSafely(root: Root): Boolean
    {
        val type = root.windowLayoutParams.get().type

        if(type == WindowManager.LayoutParams.TYPE_TOAST)
        {
            val windowToken = root.decorView.windowToken
            val token = root.decorView.applicationWindowToken

            if(windowToken === token)
            {
                return true
            }
        }

        return false
    }
}