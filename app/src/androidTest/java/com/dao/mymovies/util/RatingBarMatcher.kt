package com.dao.mymovies.util

import android.view.View
import android.widget.RatingBar
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

/**
 * Created in 10/05/19 14:35.
 *
 * @author Diogo Oliveira.
 */
class RatingBarMatcher constructor(
        private val matcher: Matcher<Float>) : BoundedMatcher<View, RatingBar>(RatingBar::class.java)
{
    override fun describeTo(description: Description)
    {
        description.appendText("is ratingBar")
    }

    override fun matchesSafely(item: RatingBar): Boolean
    {
        return matcher.matches(item.rating)
    }
}