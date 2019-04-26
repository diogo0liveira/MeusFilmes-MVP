package com.dao.mymovies.data.local.dao

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.dao.mymovies.model.Movie
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Created in 05/09/18 11:22.
 *
 * @author Diogo Oliveira.
 */
@Dao
interface MovieDAO
{
    @Insert
    fun insert(movie: Movie): Completable

    @Delete
    fun delete(movie: Movie): Completable

    @Query("SELECT * FROM Movies")
    fun getAll(): DataSource.Factory<Int, Movie>

    @Query("SELECT COUNT(1) FROM Movies WHERE id = :id")
    fun isFavorite(id: Int): Single<Boolean>
}