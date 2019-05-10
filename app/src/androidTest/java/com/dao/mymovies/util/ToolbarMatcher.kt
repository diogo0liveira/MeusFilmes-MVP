package com.dao.mymovies.util

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

/**
 * Created in 10/05/19 14:17.
 *
 * @author Diogo Oliveira.
 */
class ToolbarMatcher constructor(
        private val matcher: Matcher<CharSequence>) : BoundedMatcher<View, Toolbar>(Toolbar::class.java)
{
    override fun describeTo(description: Description)
    {
        description.appendText("is toolbar")
    }

    override fun matchesSafely(item: Toolbar): Boolean
    {
        return matcher.matches(item.title)
    }
}