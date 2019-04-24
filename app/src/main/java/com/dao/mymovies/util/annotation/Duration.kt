package com.dao.mymovies.util.annotation

import android.widget.Toast
import androidx.annotation.IntDef

/**
 * Created in 26/03/19 22:41.
 *
 * @author Diogo Oliveira.
 */
@IntDef(Toast.LENGTH_SHORT, Toast.LENGTH_LONG)
@Retention(AnnotationRetention.SOURCE)
annotation class Duration