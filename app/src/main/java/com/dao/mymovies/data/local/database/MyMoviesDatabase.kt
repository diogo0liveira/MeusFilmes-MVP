package com.dao.mymovies.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dao.mymovies.data.local.dao.MovieDAO
import com.dao.mymovies.data.local.database.converts.DateTypeConverter
import com.dao.mymovies.model.Movie

/**
 * Created in 05/09/18 11:28.
 *
 * @author Diogo Oliveira.
 */
private const val DB_NAME = "MyMoviesApp.db"
private const val DB_VERSION = 1

@TypeConverters(DateTypeConverter::class)
@Database(entities = [Movie::class], version = DB_VERSION, exportSchema = false)
abstract class MyMoviesDatabase : RoomDatabase()
{
    abstract fun movieDAO(): MovieDAO

    companion object
    {
        @Volatile
        private var database: MyMoviesDatabase? = null

        fun instance(context: Context): MyMoviesDatabase = database ?: synchronized(this) {
            database ?: buildDatabase(context).also { database = it }
        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext, MyMoviesDatabase::class.java, DB_NAME)
                        .fallbackToDestructiveMigration()
                        .build()
    }
}